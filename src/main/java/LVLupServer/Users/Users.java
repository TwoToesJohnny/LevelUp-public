package LVLupServer.Users;

import LVLupServer.LevelUpClient;
import LVLupServer.message.server.UserDetails;
import LVLupServer.message.server.leaderboard.LeaderBoardUpdate;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Managers user groups(Leaderboards)
 */

public class Users {

    private static final ReentrantLock lock = new ReentrantLock();
    private static final Comparator<LevelUpClient> positionComparator =
            Comparator.comparingInt((LevelUpClient client) -> client.userDetails.getWeekly_Score()).thenComparingInt(
                    client -> client.clientNum
            );
    private static final TreeSet<LevelUpClient> clients = new TreeSet<>(positionComparator);
    private static ScheduledFuture repeatingTask;
    private static final LeaderBoardReset leaderBoardReset;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    static {
        try {
            leaderBoardReset = new LeaderBoardReset();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void stopScheduler() {
        repeatingTask.cancel(true);
    }

    public static void addClient(LevelUpClient client) {
        lock.lock();
        clients.add(client);
        lock.unlock();
    }

    public static void setupLeaderBoardScheduler() {

        repeatingTask = scheduler.scheduleAtFixedRate(() -> {
            try {
                leaderBoardReset.updateTopUsers();
                sendLeaderBoardRewards();

                // Schedule the reset to happen 3 seconds after rewards are sent
                scheduler.schedule(() -> {
                    LeaderBoardReset.resetLeaderBoard();
                }, 3, TimeUnit.SECONDS);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, 0, 5, TimeUnit.MINUTES);
    }


    private static void sendLeaderBoardRewards() throws SQLException {
        System.out.println("Start of sendLeaderboard method reached");
        List<Integer> rewardList = leaderBoardReset.getRewards();
        List<Integer> sortedUsersList = leaderBoardReset.getSortedUsers();
        int position;
        int reward;

        Map<Integer, Integer> positionList = new HashMap<>();
        if (!sortedUsersList.isEmpty()) {
            System.out.println("Start of if statement reached");
            for (int counter = 0; counter < sortedUsersList.size(); counter++) {
                positionList.put(sortedUsersList.get(counter), counter);
            }

            for (LevelUpClient client : clients) {
                if (client.userDetails == null)
                    continue;

                position = positionList.get(client.userDetails.getUserID());
                System.out.println("position " + position);
                if (position < rewardList.size() ) {
                    reward = rewardList.get(position);
                    System.out.println("reward"  + reward);
                } else {
                    reward = 0;
                }
                System.out.println("End of sendLeaderboard method reached");
                client.send(new LeaderBoardUpdate(position + 1, reward));
            }
        }

    }
}

package Main;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;
class Demo3 extends Thread
{
    public synchronized void run()
    {
        for (int x = 0; x < 5; ++x)
            System.out.println("启动了线程Demo1");
    }
}
/**
 * The <code>ShutdownHookManager</code> enables running shutdownHook
 * in a deterministic order, higher priority first.
 * <p/>
 * The JVM runs ShutdownHooks in a non-deterministic order or in parallel.
 * This class registers a single JVM shutdownHook and run all the
 * shutdownHooks registered to it (to this class) in order based on their
 * priority.
 */


public class shutdown implements Runnable{

    private  static final shutdown MGR=new shutdown();

    private static final Logger LOG =
            LoggerFactory.getLogger(shutdown.class);
    private static final long TIMEOUT_DEFAULT = 100;
    private static final TimeUnit TIME_UNIT_DEFAULT = TimeUnit.SECONDS;

    private static final ExecutorService EXECUTOR =
            Executors.newSingleThreadExecutor(new ThreadFactoryBuilder()
                    .setDaemon(true).build());
    public void getf(long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException{
        System.out.println("timeout");
}
       public shutdown() {
            Runtime.getRuntime().addShutdownHook(new Thread(this));
          }
          public void run() {
                            MGR.shutdownInProgress.set(true);
                            System.out.println("time"+System.nanoTime());
                            for (HookEntry entry : MGR.getShutdownHooksInOrder()) {
                                Future<?> future = EXECUTOR.submit(entry.getHook());
                                try {
                                    System.out.println("time"+System.nanoTime());
                                    MGR.getf(entry.getTimeout(), entry.getTimeUnit());
                                } catch (TimeoutException ex) {
                                    future.cancel(true);
                                    LOG.warn("ShutdownHook '" + entry.getHook().getClass().
                                            getSimpleName() + "' timeout, " + ex.toString(), ex);
                                } catch (Throwable ex) {
                                    LOG.warn("ShutdownHook '" + entry.getHook().getClass().
                                            getSimpleName() + "' failed, " + ex.toString(), ex);
                                }
                            }
                            try {
                                EXECUTOR.shutdown();
                                if (!EXECUTOR.awaitTermination(TIMEOUT_DEFAULT,
                                        TIME_UNIT_DEFAULT)) {
                                    LOG.error("ShutdownHookManger shutdown forcefully.");
                                    EXECUTOR.shutdownNow();
                                }
                                LOG.debug("ShutdownHookManger complete shutdown.");
                            } catch (InterruptedException ex) {
                                LOG.error("ShutdownHookManger interrupted while waiting for " +
                                        "termination.", ex);
                                EXECUTOR.shutdownNow();
                                Thread.currentThread().interrupt();
                            }
                        }


               /**
                * Return <code>ShutdownHookManager</code> singleton.
                *
                * @return <code>ShutdownHookManager</code> singleton.
                */

   public static shutdown get() {
        return MGR;
    }
    public static void getall() {
        System.out.println("getll");
    }
    /**
     * Private structure to store ShutdownHook, its priority and timeout
     * settings.
     */
    static class HookEntry {
        private final Runnable hook;
        private final int priority;
        private final long timeout;
        private final TimeUnit unit;

        HookEntry(Runnable hook, int priority) {
            this(hook, priority, TIMEOUT_DEFAULT, TIME_UNIT_DEFAULT);
        }

        HookEntry(Runnable hook, int priority, long timeout, TimeUnit unit) {
            this.hook = hook;
            this.priority = priority;
            this.timeout = timeout;
            this.unit = unit;
            System.out.println(" HookEntry"+ hook);
        }

        @Override
        public int hashCode() {
            return hook.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            boolean eq = false;
            if (obj != null) {
                if (obj instanceof HookEntry) {
                    eq = (hook == ((HookEntry)obj).hook);
                }
            }
            return eq;
        }

        Runnable getHook() {
            return hook;
        }

        int getPriority() {
            return priority;
        }

        long getTimeout() {
            return timeout;
        }

        TimeUnit getTimeUnit() {
            return unit;
        }
    }

    private final Set<HookEntry> hooks =Collections.synchronizedSet(new HashSet<HookEntry>());
    private AtomicBoolean shutdownInProgress = new AtomicBoolean(false);

    //private to constructor to ensure singularity


    /**
     * Returns the list of shutdownHooks in order of execution,
     * Highest priority first.
     *
     * @return the list of shutdownHooks in order of execution.
     */
    List<HookEntry> getShutdownHooksInOrder() {
        //System.err.println("hook"+MGR.hooks);
        List<HookEntry> list;
        synchronized (MGR.hooks) {
            list = new ArrayList<HookEntry>(MGR.hooks);
        }
        Collections.sort(list, new Comparator<HookEntry>() {

            //reversing comparison so highest priority hooks are first

            public int compare(HookEntry o1, HookEntry o2) {
                return o2.priority - o1.priority;
            }
        });
        return list;
    }

    /**
     * Adds a shutdownHook with a priority, the higher the priority
     * the earlier will run. ShutdownHooks with same priority run
     * in a non-deterministic order.
     *
     * @param shutdownHook shutdownHook <code>Runnable</code>
     * @param priority priority of the shutdownHook.
     */
    public void addShutdownHook(Runnable shutdownHook, int priority) {
        System.err.println("shutdownHook"+shutdownHook);
        if (shutdownHook == null) {
            throw new IllegalArgumentException("shutdownHook cannot be NULL");
        }
        if (shutdownInProgress.get()) {
            throw new IllegalStateException("Shutdown in progress, cannot add a " +
                    "shutdownHook");
        }
        hooks.add(new HookEntry(shutdownHook, priority));
    }

    /**
     *
     * Adds a shutdownHook with a priority and timeout the higher the priority
     * the earlier will run. ShutdownHooks with same priority run
     * in a non-deterministic order. The shutdown hook will be terminated if it
     * has not been finished in the specified period of time.
     *
     * @param shutdownHook shutdownHook <code>Runnable</code>
     * @param priority priority of the shutdownHook
     * @param timeout timeout of the shutdownHook
     * @param unit unit of the timeout <code>TimeUnit</code>
     */
    public void addShutdownHook(Runnable shutdownHook, int priority, long timeout,
                                TimeUnit unit) {
        if (shutdownHook == null) {
            throw new IllegalArgumentException("shutdownHook cannot be NULL");
        }
        if (shutdownInProgress.get()) {
            throw new IllegalStateException("Shutdown in progress, cannot add a " +
                    "shutdownHook");
        }

        hooks.add(new HookEntry(shutdownHook, priority, timeout, unit));
        System.out.println("MGR.hooks..." + MGR + "......" + MGR.hooks);

    }

    /**
     * Removes a shutdownHook.
     *
     * @param shutdownHook shutdownHook to remove.
     * @return TRUE if the shutdownHook was registered and removed,
     * FALSE otherwise.
     */
    public boolean removeShutdownHook(Runnable shutdownHook) {
        if (shutdownInProgress.get()) {
            throw new IllegalStateException("Shutdown in progress, cannot remove a " +
                    "shutdownHook");
        }
        return hooks.remove(new HookEntry(shutdownHook, 0));
    }

    /**
     * Indicates if a shutdownHook is registered or not.
     *
     * @param shutdownHook shutdownHook to check if registered.
     * @return TRUE/FALSE depending if the shutdownHook is is registered.
     */
    public boolean hasShutdownHook(Runnable shutdownHook) {
        return hooks.contains(new HookEntry(shutdownHook, 0));
    }

    /**
     * Indicates if shutdown is in progress or not.
     *
     * @return TRUE if the shutdown is in progress, otherwise FALSE.
     */
    public boolean isShutdownInProgress() {
        return shutdownInProgress.get();
    }

    /**
     * clear all registered shutdownHooks.
     */
    public void clearShutdownHooks() {
        hooks.clear();
    }

    public static void main(String[] args) {



        //MGR.addShutdownHook(demo1, 10, 100, shutdown.TIME_UNIT_DEFAULT);
        System.out.println("The Application is doing something");
    }

}


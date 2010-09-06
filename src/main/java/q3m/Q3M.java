/*
 * The MIT License
 *
 * Copyright (c) 2010 Nikolas Lotz <nikolas.lotz@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package q3m;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;

/**
 * General purpose utility class.
 * 
 * @author nlotz
 */
public class Q3M {

    /**
     * Log level DEBUG.
     */
    public static final int DEBUG = 100;

    /**
     * Log level INFO.
     */
    public static final int INFO = 200;

    /**
     * Log level WARN.
     */
    public static final int WARN = 300;

    /**
     * Log level ERROR.
     */
    public static final int ERROR = 400;

    /**
     * Current log level.
     */
    public static int logLevel = INFO;

    /**
     * Current log writer.
     */
    public static PrintStream logWriter = System.out;

    /**
     * Closes an input stream.
     * 
     * @param in The input stream to close
     */
    public static void close(InputStream in) {
        try {
            if (in != null) {
                in.close();
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * Logs a DEBUG message.
     * 
     * @param message The message to log
     */
    public static void debug(String message) {
        if (logLevel <= DEBUG) {
            logWriter.println("[DEBUG] " + message);
            logWriter.flush();
        }
    }

    /**
     * Logs an ERROR message.
     * 
     * @param message The message to log
     */
    public static void error(String message) {
        if (logLevel <= ERROR) {
            logWriter.println("[ERROR] " + message);
            logWriter.flush();
        }
    }

    /**
     * Finds the resource with the given path.
     * 
     * @param path The resource path
     * @return A <code>URL</code> object for reading the resource,
     *     or <code>null</code> if the resource could not be found
     */
    public static URL getRes(String path) {
        return Q3M.class.getResource(path);
    }

    /**
     * Finds the resource with the given path.
     * 
     * @param path The resource path
     * @return An input stream for reading the resource,
     *     or <code>null</code> if the resource could not be found
     */
    public static InputStream getResStream(String path) {
        return Q3M.class.getResourceAsStream(path);
    }

    /**
     * Logs an INFO message.
     * 
     * @param message The message to log
     */
    public static void info(String message) {
        if (logLevel <= INFO) {
            logWriter.println("[ INFO] " + message);
            logWriter.flush();
        }
    }

    /**
     * Logs a WARN message.
     * 
     * @param message The message to log
     */
    public static void warn(String message) {
        if (logLevel <= WARN) {
            logWriter.println("[ WARN] " + message);
            logWriter.flush();
        }
    }

}

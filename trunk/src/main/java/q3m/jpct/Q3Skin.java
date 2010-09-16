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
package q3m.jpct;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Hashtable;
import java.util.StringTokenizer;

import q3m.Q3M;

/**
 * Quake III skin.
 * 
 * @author nlotz
 */
public class Q3Skin {

    /**
     * Mesh shaders.
     */
    public Hashtable shaders;

    /**
     * Reads a skin from a resource path.
     * 
     * @param path the resource path
     * @throws IOException
     */
    public Q3Skin(String path) throws IOException {
        this(Q3M.getResStream(path));
    }

    /**
     * Reads a skin from a stream.
     * 
     * @param in the stream to read from
     * @throws IOException
     */
    public Q3Skin(InputStream in) throws IOException {

        shaders = new Hashtable();

        String line = null;
        Reader r = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(r);
        while (null != (line = br.readLine())) {

            line = line.trim();
            if ((line.length() < 1) || (line.startsWith("//"))) {
                continue;
            }

            StringTokenizer st = new StringTokenizer(line, ",");
            if (st.countTokens() != 2) {
                continue;
            }

            shaders.put(trim(st.nextToken()), trim(st.nextToken()));
        }
    }

    private String trim(String s) {
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length() - 1);
        }
        return s;
    }

}

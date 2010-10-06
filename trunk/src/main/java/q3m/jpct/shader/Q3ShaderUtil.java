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
package q3m.jpct.shader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import q3m.Q3M;

/**
 * Quake III shader utility class.
 * 
 * @author nlotz
 */
public class Q3ShaderUtil {

    private static final int STATE_ID = 0;

    private static final int STATE_START = 1;

    private static final int STATE_CFG = 2;

    private static final int STATE_DEF = 3;

    private static Q3ShaderUtil instance = null;

    /**
     * Returns the shaderutil singleton.
     * 
     * @return
     */
    public synchronized static Q3ShaderUtil getInstance() {
        return (instance == null) ? (instance = new Q3ShaderUtil()) : instance;
    }

    private Hashtable shaderDefs;

    private Q3ShaderUtil() {
        shaderDefs = new Hashtable();
    }

    /**
     * Reads shader definitions from a stream.
     * 
     * @param in the stream to read from (.shader resource)
     * @throws IOException
     */
    public void addShaderDefs(InputStream in) throws IOException {

        String line = null;
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        while (null != (line = br.readLine())) {
            line = line.trim();
            if ((line.length() < 1) || (line.startsWith("//")))
                continue;
            sb.append(line + "\n");
        }

        int state = STATE_ID;
        int stage = 0;
        int numDefs = 0;
        Q3ShaderDef def = null;
        StringTokenizer st = new StringTokenizer(sb.toString(), "\n{}", true);
        while (st.hasMoreTokens()) {
            String s = st.nextToken().trim();
            if (s.length() < 1)
                continue;
            switch (state) {
            case STATE_ID:
                def = new Q3ShaderDef(s.toLowerCase());
                shaderDefs.put(def.getId(), def);
                state = STATE_START;
                numDefs++;
                break;
            case STATE_START:
                if (!s.equals("{"))
                    throw new IOException("'{' expected");
                state = STATE_CFG;
                break;
            case STATE_CFG:
                if (s.equals("{")) {
                    state = STATE_DEF;
                    break;
                }
                if (s.equals("}")) {
                    state = STATE_ID;
                    stage = 0;
                    def = null;
                    break;
                }
                if (def != null) {
                    def.addCfgDef(tokenize(s));
                }
                break;
            case STATE_DEF:
                if (s.equals("}")) {
                    state = STATE_CFG;
                    stage++;
                    break;
                }
                if (def != null) {
                    def.addStageDef(stage, tokenize(s));
                }
                break;
            default:
                throw new IOException("invalid state: " + state);
            }
        }

        Q3M.debug("parsed " + numDefs + " shader definition(s)");
    }

    /**
     * Returns a shader definition.
     * 
     * @param id the shader id
     * @return the shader definition or <code>null</code> if not found
     */
    public Q3ShaderDef getShaderDef(String id) {
        return (Q3ShaderDef) shaderDefs.get(id.toLowerCase());
    }

    private static Vector tokenize(String s) {
        Vector v = new Vector();
        StringTokenizer st = new StringTokenizer(s, " \t", false);
        while (st.hasMoreTokens()) {
            String token = st.nextToken().trim();
            if (token.length() < 1)
                continue;
            if (token.startsWith("//"))
                break;
            if (token.startsWith("\"")) {
                while ((!token.endsWith("\"")) && st.hasMoreTokens())
                    token += " " + st.nextToken().trim();
                if (token.endsWith("\""))
                    token = token.substring(1, token.length() - 1);
            }
            v.add(token);
        }
        return v;
    }

}

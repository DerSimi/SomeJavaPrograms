import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) by Simon R./DerSimi 2014-2018
 * <p>
 * Licensed under the 4-clause BSD license (the "License");
 * you may not use this file except in compliance with
 * the License.
 * <p>
 * https://directory.fsf.org/wiki/License:BSD-4-Clause
 * LICENSE file
 * <p>
 * THIS SOFTWARE IS PROVIDED BY Simon R./DerSimi "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Simon R./DerSimi BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <p>
 * This file was created by Simon R./DerSimi on 10.09.2018
 */

/** 
 * This file is a few years old. It was used to search in a string for a specific char sequence and to identify the match
 */
public class StringContains {

    private List<Result> results;

    public StringContains(String string, String search) {
        results = contains(string, search);
    }

    public class Result {

        private String word, similaresResult;

        private int start, stop, similar;

        public Result(String word, String similaresResult, int start, int stop, int similar) {
            this.word = word;
            this.similaresResult = similaresResult;
            this.start = start;
            this.stop = stop;
            this.similar = similar;
        }

        public String getWord() {
            return word;
        }

        public String getSimilaresResult() {
            return similaresResult;
        }

        public int getStart() {
            return start;
        }

        public int getStop() {
            return stop;
        }

        public int getSimilar() {
            return similar;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "word='" + word + '\'' +
                    ", similaresResult='" + similaresResult + '\'' +
                    ", start=" + start +
                    ", stop=" + stop +
                    ", similar=" + similar +
                    '}';
        }
    }

    private String build(String string, int start, int stop) {
        if (string.length() < start ^ string.length() < stop)
            return null;

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = start; i < stop; i++) {
            stringBuilder.append(string.charAt(i));
        }

        return stringBuilder.toString();
    }

    private int similar(String string, String string1) {
        if (string.length() > string1.length())
            return similar(string1, string);

        char[] charArray = string.toCharArray();

        int i = 0;
        int same = 1;

        for (char c : string1.toCharArray()) {
            i++;

            if (charArray.length >= i && c == charArray[i - 1])
                same++;
        }

        if (same == 0)
            return 0;

        if (i == 0)
            return 0;

        return (same - 1) * 100 / i;
    }

    private List<Result> contains(String string, String search) {
        List<Result> results = new ArrayList<>();

        if (string.length() == search.length()) {
            int similar = similar(string, search);

            if (similar >= 60)
                results.add(new Result(string, search, 0, search.length(), similar));

            return results;
        }

        /*
            Normal contains
         */

        int lastResult = 0;

        while (lastResult != -1) {
            int result = lastResult == 0 ? 0 : lastResult + search.length();

            lastResult = string.indexOf(search, result);

            if (lastResult != -1)
                results.add(new Result(search, search, lastResult, lastResult + search.length(), 100));
        }

        if (!results.isEmpty())
            return results;

        /*
            Similar contains
         */

        if (search.length() <= 3)
            return results;

        int deviation = 0;

        List<Integer> integers = new ArrayList<>();

        for (char c : search.toCharArray()) {
            for (int i = 0; i < string.length(); i++) {
                char c1 = string.charAt(i);

                if (c == c1) {
                    int start = i == 0 ? 0 : (i - deviation < 0 ? 0 : i - deviation);
                    int stop = i + search.length() - deviation;

                    if (stop > string.length()) {
                        stop = string.length();
                    }

                    String build = build(string, start, stop);

                    int similar = similar(search, build);

                    if (similar >= 60 && !integers.contains(start + stop)) {
                        results.add(new Result(search, build, start, stop, similar));
                        integers.add(start + stop);
                    }
                }
            }

            deviation++;
        }

        integers.clear();

        return results;
    }

    public List<Result> getResults() {
        return results;
    }
}

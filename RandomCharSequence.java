import java.security.SecureRandom;
import java.util.StringJoiner;

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
public class RandomCharSequence {

    private StringBuilder stringBuilder;

    public RandomCharSequence(int length, RandomCharSequenceType randomCharSequenceType, String extraChars) {
        if (length == 0) throw new IllegalArgumentException("length cannot be zero");
        if (randomCharSequenceType == null) throw new IllegalArgumentException("randomCharSequenceType cannot be null");

        this.stringBuilder = new StringBuilder();

        String chars;
        if (extraChars.length() == 0)
            chars = randomCharSequenceType.chars;
        else
            chars = new StringJoiner("").add(randomCharSequenceType.chars).add(extraChars).toString();


        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++)
            stringBuilder.append(chars.charAt(secureRandom.nextInt(chars.length())));

    }

    public RandomCharSequence(int length) {
        this(length, RandomCharSequenceType.ALPHANUMERIC, "");
    }

    public RandomCharSequence(int length, RandomCharSequenceType randomCharSequenceType) {
        this(length, randomCharSequenceType, "");
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }

    public enum RandomCharSequenceType {
        ALPHA("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"), NUMERIC("0123456789"), ALPHANUMERIC("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");

        private String chars;

        RandomCharSequenceType(String chars) {
            this.chars = chars;
        }
    }
}

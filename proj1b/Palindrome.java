public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        if (word == null) {
            return null;
        }
        Deque<Character> deque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    /* Recursion with a private helper method */
    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        return isPalindromeHelper(deque);
    }

    /* private helper method for isPalindrome() {} */
    private boolean isPalindromeHelper(Deque<Character> inque) {
        if (inque.size() <= 1) {
            return true;
        }
        Character front = inque.removeFirst();
        Character back = inque.removeLast();
        if (front == back) {
            return isPalindromeHelper(inque);
        }
        return false;
    }

    /* implements CharacterComparator */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = wordToDeque(word);
        return isPalindromeHelper(deque, cc);
    }

    private boolean isPalindromeHelper(Deque<Character> inque, CharacterComparator cc) {
        if (inque.size() <= 1) {
            return true;
        }
        Character front = inque.removeFirst();
        Character back = inque.removeLast();
        return cc.equalChars(front, back) && isPalindromeHelper(inque, cc);
    }
}

public class OffByN implements CharacterComparator {
    int N;
    public OffByN(int num) {
        N = num;
    }
    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == N;
    }
}
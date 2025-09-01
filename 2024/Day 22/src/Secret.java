import java.util.*;

public class Secret {
    public long secret;
    public List<Integer> changes = new ArrayList<>();
    private int prev;

    public Map<List<Integer>, Integer> sequences = new HashMap<>();
    public Secret(long secret) {
        this.secret = secret;
        this.prev = (int) this.secret % 10;
    }

    public void next() {
        //First step
        mix(this.secret * 64L);
        //Second step
        mix(Math.floorDiv(this.secret, 32L));
        //Third step
        mix(this.secret * 2048L);
        int current = (int) this.secret % 10;
        changes.add(current - prev);
        if(changes.size() >= 4) {
            List<Integer> sequence = List.of(changes.get(changes.size() - 4),changes.get(changes.size() - 3), changes.get(changes.size() - 2), changes.get(changes.size() - 1));
            if(!sequences.containsKey(sequence)) {
                sequences.put(sequence, current);
            }
        }
        prev = current;
    }

    private void mix(long value) {
        this.secret ^= value;
        this.prune();
    }

    private void prune() {
        this.secret = this.secret % 16777216L;
    }
}

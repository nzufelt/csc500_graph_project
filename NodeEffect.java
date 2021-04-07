// a class that should eventually become abstract, to determine whether propogation
// happens

public class NodeEffect {
    private boolean affected;

    public NodeEffect() {
        affected = false;
    }

    public boolean getAffected() {
        return affected;
    }

    public boolean affect() {
        affected = true;
        return affected;
    }

    // current version has a 100% affection rate; this is something to override in
    // general
    public boolean shouldAffect(BasicNode other) {
        return affected;
    }
}

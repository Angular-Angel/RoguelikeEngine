package roguelikeengine.display;

/**
 *
 * @author greg
 */
public enum Rotation{
    
    degree0(0), degree45(1), degree90(2), degree135(3), degree180(4),
    degree225(5), degree270(6), degree315(7);
    
    public final int ordinal;
    
    
    private Rotation(int ordinal) {
        this.ordinal = ordinal;
    }
    
    /**
     * Adds the two rotations.
     * @param r1 The first rotation to add.
     * @param r2 The second rotation to add.
     * @return The sum of the rotations.
     */
    public static Rotation add(Rotation r1, Rotation r2) {
        return values()[(r1.ordinal + r2.ordinal) % 8];
    }
    
    /**
     * Subtracts the second rotation from the first one.
     * @param r1 The first rotation.
     * @param r2 The second rotation.
     * @return The result of the two rotations.
     */
    public static Rotation subtract(Rotation r1, Rotation r2) {
        return values()[(r1.ordinal - r2.ordinal) % 8];
    }
}


package demo.rva;
public class BigMonster extends Monster{
    private int z;

    public BigMonster(int level) {
        super(level);
        int randomInt = (int) (Math.random() * ((100 * level - 1) + 1)) + 1;
        this.z = randomInt;
        this.setResult(this.getX() * this.getY() + this.z);
        this.setMessage(String.format("Реши пример: %d * %d + %d = ?", this.getX(), this.getY(), this.z));
    }

}

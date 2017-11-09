public class DiskReader implements DataProvider<Integer, String> {

    // This simulated data provider returns a string matching the integer key for numbers 0 to 100 (inclusive)
    // and null otherwise

    @Override
    public String get(Integer key) {
        if(key < 100 && key >=0) {
            return key + "";
        }
        return null;
    }
}

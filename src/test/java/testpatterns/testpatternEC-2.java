class TestPatternEC_2 implements NotCloneable {
    public Object clone() throws CloneNotSupportedException {
    	// do something
    	return new Object();
    }
}

interface NotCloneable{}
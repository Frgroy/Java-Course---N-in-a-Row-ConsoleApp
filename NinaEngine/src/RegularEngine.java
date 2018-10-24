import XsdClass.GameDescriptor;

import java.io.Serializable;

public class RegularEngine extends Engine implements Serializable {
    public RegularEngine(GameDescriptor g) {
        super(g);
    }
}

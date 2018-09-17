package ifood.score.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {

    public static final int RELEVANCE_SCALE = 9;
    public static final RoundingMode ROUND_MODE = RoundingMode.HALF_EVEN;

    public static BigDecimal scale(BigDecimal value) {
        return value.setScale(RELEVANCE_SCALE, ROUND_MODE);
    }
}

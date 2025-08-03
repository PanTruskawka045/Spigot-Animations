package me.pan_truskawka045.effects3d.points;

import com.google.common.base.Preconditions;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class PointUtil {

    /**
     * Returns the minimum point
     *
     * @param first  first point
     * @param second second point
     * @return Point with minimum coordinates
     */
    public @NotNull Point minPoint(@NotNull Point first, @NotNull Point second) {
        Preconditions.checkNotNull(first, "first point cannot be null");
        Preconditions.checkNotNull(second, "second point cannot be null");
        return new Point(Math.min(first.getX(), second.getX()), Math.min(first.getY(), second.getY()), Math.min(first.getZ(), second.getZ()));
    }

    /**
     * Returns the maximum point
     *
     * @param first  first point
     * @param second second point
     * @return Point with maximum coordinates
     */
    public @NotNull Point maxPoint(@NotNull Point first, @NotNull Point second) {
        Preconditions.checkNotNull(first, "first point cannot be null");
        Preconditions.checkNotNull(second, "second point cannot be null");
        return new Point(Math.max(first.getX(), second.getX()), Math.max(first.getY(), second.getY()), Math.max(first.getZ(), second.getZ()));
    }

}

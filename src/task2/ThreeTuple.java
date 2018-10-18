package task2;

public class ThreeTuple<E, F, G> {
    public E first;
    public F second;
    public G third;

    public ThreeTuple(E first, G third) {
        this.first = first;
        this.third = third;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ThreeTuple)
        {
            if (((ThreeTuple) obj).first.equals(this.first))
            {
                if (((ThreeTuple) obj).second.equals(this.second))
                {
                    if (((ThreeTuple) obj).third.equals(this.third))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}

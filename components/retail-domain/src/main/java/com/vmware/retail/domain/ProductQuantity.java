package com.vmware.retail.domain;

/**
 * ProductQuantity
 *
 * @author Gregory Green
 */
public record ProductQuantity(Product product,long quantity) implements Comparable<ProductQuantity>
{
    @Override
    public int compareTo(ProductQuantity o)
    {
        if(o == null)
            return 1;


        if(quantity > o.quantity)
            return 1;
        else if(quantity < o.quantity)
            return -1;
        else
        {
            if(product == null)
                return -1;
            if(o.product == null)
                return 1;

            return String.valueOf(product.name()).compareTo(o.product.name());
        }
    }

}

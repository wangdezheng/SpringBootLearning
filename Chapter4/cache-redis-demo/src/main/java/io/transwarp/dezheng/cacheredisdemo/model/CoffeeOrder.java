package io.transwarp.dezheng.cacheredisdemo.model;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "T_ORDER")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CoffeeOrder extends BaseEntity implements Serializable {

    private String customer;
    @ManyToMany
    @JoinTable(name = "T_ORDER_COFEE")
    @OrderBy("id")
    private List<Coffee> items;
    @Enumerated
    @Column(nullable = false)
    private OrderState state;

}

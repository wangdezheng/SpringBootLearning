package io.transwarp.dezheng.cacheredisdemo.model;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.money.Money;import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "T_COFFEE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString(callSuper = true)
public class Coffee extends BaseEntity implements Serializable {
    private String name;
    @Column
    @Type(type="org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount",
        parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode",value = "CNY")})
    private Money price;
}

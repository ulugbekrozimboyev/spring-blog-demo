package uz.ulugbek.blog.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "ACCOUNTS")
@Data
public class Account implements Serializable {

    @Id
    @Column(name = "ID")
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ")
    @SequenceGenerator(sequenceName = "ACCOUNTS_SEQ", allocationSize = 1, name = "CUST_SEQ")
    private Long id;

    @Column(name = "username")
    private String username;

    private String password;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "ACTIVE")
    private int active;

    @Column(name = "FIO")
    private String fio;
}

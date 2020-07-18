package br.com.haagsma.productcontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SYSTEM_USER_CORE")
@Data
public class User implements Serializable {

    /**
     * id	integer
     * name	character varying	150
     * email	character varying	255
     * password	character varying	100
     * status_id	integer
     * create_date	timestamp without time zone
     * update_date	timestamp without time zone
     */


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;
}

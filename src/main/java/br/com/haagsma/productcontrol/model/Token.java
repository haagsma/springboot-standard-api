package br.com.haagsma.productcontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TOKEN")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Token implements Serializable {

    /**
     * id	integer
     * token	character varying
     * create_date	timestamp without time zone
     * expire_date	timestamp without time zone
     * is_valid	boolean
     * user_id	integer
     */


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;

    @CreationTimestamp
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "expire_date")
    private Date expireDate;

    @Column(name = "is_valid")
    private Boolean isValid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}

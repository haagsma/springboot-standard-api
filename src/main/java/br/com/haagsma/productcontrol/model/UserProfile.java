package br.com.haagsma.productcontrol.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "USER_PROFILE")
@Data
public class UserProfile implements Serializable {

    /**
     * id	integer
     * user_id	integer
     * profile_id	integer
     * create_date	timestamp without time zone
     */


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @CreationTimestamp
    @Column(name = "create_date")
    private Date createDate;
}

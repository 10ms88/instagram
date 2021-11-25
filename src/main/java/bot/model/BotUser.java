package bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Entity
@Table(name = "BOT_USER")
@Data
@AllArgsConstructor
@ToString
public class BotUser {
    public BotUser() {
    }


    @Id
    public String username;
    @Column
    public boolean is_private;
    @Column
    public boolean is_verified;
    @Column
    public boolean has_chaining;
    @Column
    public boolean is_business;
    @Column
    public int media_count;
    @Column
    public String profile_pic_id;
    @Column
    public String external_url;
    @Column
    public String full_name;
    @Column
    public boolean has_biography_translation;
    @Column
    public boolean has_anonymous_profile_picture;
    @Column
    public boolean is_favorite;
    @Column
    public String public_phone_country_code;
    @Column
    public String public_phone_number;
    @Column
    public String public_email;
    @Column
    public long pk;
    @Column
    public int geo_media_count;
    @Column
    public int usertags_count;
    @Column
    public String address_street;
    @Column
    public String city_name;
    @Column
    public String zip;
    @Column
    public String direct_messaging;
    @Column
    public String business_contact_method;
    @Column(length = 2000)
    public String biography;
    @Column
    public int follower_count;
    @Column
    public int following_count;
    @Column
    public float latitude;
    @Column
    public float longitude;
    @Column
    public String category;
}

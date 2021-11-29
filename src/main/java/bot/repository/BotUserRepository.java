package bot.repository;

import bot.model.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BotUserRepository extends JpaRepository<BotUser, String> {

    @Query(value = "select * from BOT_USER where media_count = 0 limit 10000", nativeQuery = true)
    List<BotUser> findByNoPublications();

    @Query(value = "select * from BOT_USER where media_count > 1000 limit 10000", nativeQuery = true)
    List<BotUser> findByPublicationsOver1000();

    @Query(value = "select * from BOT_USER where profile_pic_id is null limit 10000", nativeQuery = true)
    List<BotUser> findByNoAvatar();

    @Query(value = "select * from BOT_USER where following_count > 1000 limit 10000", nativeQuery = true)
    List<BotUser> findBySubscriptionsOver1000();

    @Query(value = "select * from BOT_USER where following_count > 2000 limit 10000", nativeQuery = true)
    List<BotUser> findBySubscriptionsOver2000();

    @Query(value = "select * from BOT_USER where following_count > 3000 limit 10000", nativeQuery = true)
    List<BotUser> findBySubscriptionsOver3000();

    @Query(value = "select * from BOT_USER where bot_user.follower_count > 1000 limit 10000", nativeQuery = true)
    List<BotUser> findBySubscribersOver1000();

    @Query(value = "select * from BOT_USER where bot_user.follower_count > 2000 limit 10000", nativeQuery = true)
    List<BotUser> findBySubscribersOver2000();

    @Query(value = "select * from BOT_USER where bot_user.follower_count > 3000 limit 10000", nativeQuery = true)
    List<BotUser> findBySubscribersOver3000();

    @Query(value = "select * from BOT_USER where bot_user.follower_count < bot_user.following_count limit 10000", nativeQuery = true)
    List<BotUser> findBySubscriptionsOverSubscribers();

    @Query(value = "select * from BOT_USER where bot_user.follower_count > bot_user.following_count limit 10000", nativeQuery = true)
    List<BotUser> findBySubscribersOverSubscriptions();

    @Query(value = "select * from BOT_USER where is_business = true limit 10000", nativeQuery = true)
    List<BotUser> findByIsBusiness();

    @Query(value = "select * from BOT_USER where is_private = true limit 10000", nativeQuery = true)
    List<BotUser> findByIsPrivate();

    @Query(value = "select * from BOT_USER where public_phone_number is not null and length(public_phone_number) > 0 limit 10000", nativeQuery = true)
    List<BotUser> findByPhoneNumberSet();

    @Query(value = "select * from BOT_USER where bot_user.external_url is not null and length(bot_user.external_url) > 0 limit 10000", nativeQuery = true)
    List<BotUser> findByLinkSet();

    @Query(value = "select * from BOT_USER where bot_user.public_email is not null and length(bot_user.public_email) > 0 limit 10000", nativeQuery = true)
    List<BotUser> findByEmailSet();

    @Query(value = "select * from BOT_USER  limit 10000", nativeQuery = true)
    List<BotUser> findAllLimit();


    @Query(value = "select count(username) from BOT_USER ", nativeQuery = true)
    int getTotalUsersBot();


    @Query(value = "select count(username) from( select * from BOT_USER where media_count = 0 ) as s", nativeQuery = true)
    int countFindByNoPublications();

    @Query(value = "select count(username) from( select * from BOT_USER where media_count > 1000)as s", nativeQuery = true)
    int countFindByPublicationsOver1000();

    @Query(value = "select count(username) from( select * from BOT_USER where profile_pic_id is null )as s", nativeQuery = true)
    int countFindByNoAvatar();

    @Query(value = "select count(username) from( select * from BOT_USER where following_count > 1000 )as s", nativeQuery = true)
    int countFindBySubscriptionsOver1000();

    @Query(value = "select count(username) from( select * from BOT_USER where following_count > 2000 )as s", nativeQuery = true)
    int countFindBySubscriptionsOver2000();

    @Query(value = "select count(username) from( select * from BOT_USER where following_count > 3000 )as s", nativeQuery = true)
    int countFindBySubscriptionsOver3000();

    @Query(value = "select count(username) from( select * from BOT_USER where bot_user.follower_count > 1000 )as s", nativeQuery = true)
    int countFindBySubscribersOver1000();

    @Query(value = "select count(username) from( select * from BOT_USER where bot_user.follower_count > 2000 )as s", nativeQuery = true)
    int countFindBySubscribersOver2000();

    @Query(value = "select count(username) from( select * from BOT_USER where bot_user.follower_count > 3000 )as s", nativeQuery = true)
    int countFindBySubscribersOver3000();

    @Query(value = "select count(username) from( select * from BOT_USER where bot_user.follower_count < bot_user.following_count )as s", nativeQuery = true)
    int countFindBySubscriptionsOverSubscribers();

    @Query(value = "select count(username) from( select * from BOT_USER where bot_user.follower_count > bot_user.following_count )as s", nativeQuery = true)
    int countFindBySubscribersOverSubscriptions();

    @Query(value = "select count(username) from( select * from BOT_USER where is_business = true )as s", nativeQuery = true)
    int countFindByIsBusiness();

    @Query(value = "select count(username) from( select * from BOT_USER where is_private = true )as s", nativeQuery = true)
    int countFindByIsPrivate();

    @Query(value = "select count(username) from( select * from BOT_USER where public_phone_number is not null and length(public_phone_number) > 0 )as s", nativeQuery = true)
    int countFindByPhoneNumberSet();

    @Query(value = "select count(username) from( select * from BOT_USER where bot_user.external_url is not null and length(bot_user.external_url) > 0 )as s", nativeQuery = true)
    int countFindByLinkSet();

    @Query(value = "select count(username) from( select * from BOT_USER where bot_user.public_email is not null and length(bot_user.public_email) > 0)as s", nativeQuery = true)
    int countFindByEmailSet();


}




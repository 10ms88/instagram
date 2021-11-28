package bot.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {

    public static final String USER_NAME = "username";
    public static final String FULL_NAME = "full_name";
    public static final String MEDIA_COUNT = "media_count";
    public static final String FOLLOWER_COUNT = "follower_count";
    public static final String FOLLOWING_COUNT = "following_count";
    public static final String IS_BUSINESS = "_business";
    public static final String IS_PRIVATE = "_private";
    public static final String EXTERNAL_URL = "external_url";
    public static final String PUBLIC_PHONE_NUMBER = "public_phone_number";
    public static final String PUBLIC_EMAIL = "public_email";
    public static final String BIOGRAPHY = "biography";
    public static final String CATEGORY = "category";

    public static final List<String> COLUMNS_LIST = Collections.unmodifiableList(
            Arrays.asList(USER_NAME, FULL_NAME, MEDIA_COUNT, FOLLOWER_COUNT,
                    FOLLOWING_COUNT, IS_PRIVATE, IS_BUSINESS, EXTERNAL_URL,
                     PUBLIC_PHONE_NUMBER, PUBLIC_EMAIL, CATEGORY, BIOGRAPHY));


    public static final String findByNoPublications = "отсутствуют публикации";
    public static final String findByPublicationsOver1000 = "публикаций > 1000";
    public static final String findByNoAvatar = "отсутствует аватар";
    public static final String findBySubscriptionsOver1000 = "подписок более 1000";
    public static final String findBySubscriptionsOver2000 = "подписок более 2000";
    public static final String findBySubscriptionsOver3000 = "подписок более 3000";
    public static final String findBySubscribersOver1000 = "подписчиков более 1000";
    public static final String findBySubscribersOver2000 = "подписчиков более 2000";
    public static final String findBySubscribersOver3000 = "подписчиков более 3000";
    public static final String findBySubscriptionsOverSubscribers = "подписок > подписчиков";
    public static final String findBySubscribersOverSubscriptions = "подписок < подписчиков";
    public static final String findByIsBusiness = "бизнесс аккаунт";
    public static final String findByIsPrivate = "закрытый аккаунт";
    public static final String findByPhoneNumberSet = "указан номер телефона";
    public static final String findByLinkSet = "ссылка на сайт в описании";
    public static final String findByEmailSet = "указан email";
    public static final String findAll = "все пользователи";

    public static final List<String> FILTERS_LIST = Collections.unmodifiableList(
            Arrays.asList(findByNoPublications, findByPublicationsOver1000, findByNoAvatar, findAll,
                    findBySubscriptionsOver1000, findBySubscriptionsOver2000, findBySubscriptionsOver3000, findBySubscribersOver1000,
                    findBySubscribersOver2000, findBySubscribersOver3000, findBySubscriptionsOverSubscribers, findBySubscribersOverSubscriptions,
                    findByIsBusiness, findByIsPrivate, findByPhoneNumberSet, findByLinkSet, findByEmailSet));


}

package bot.ui;

import bot.config.ApplicationContextProvider;
import bot.config.Props;
import bot.model.BotUser;
import bot.repository.BotUserRepository;
import bot.utils.Constants;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route("q")
public class BotUserList extends HorizontalLayout {

    private final Grid<BotUser> BOT_USER_GRID = new Grid<>(BotUser.class, false);
    BotUserRepository botUserRepository = ApplicationContextProvider.getApplicationContext().getBean(BotUserRepository.class);
    private final VerticalLayout TOOLBAR = new VerticalLayout();
    private final int LIMIT_BOT_USER_LIST = 1000;
    private final List<BotUser> BOT_USER_LIST = botUserRepository.findAllLimit(LIMIT_BOT_USER_LIST);
    private List<BotUser> botUserListFiltered = BOT_USER_LIST;
    private final TextArea TEXT_AREA = new TextArea();
    private final int TOTAL_BOT_USERS =botUserRepository.getTotalUsersBot();




    @Autowired
    public BotUserList() {
        VerticalLayout horizontalLayout = new VerticalLayout();
        horizontalLayout.setWidth("80%");
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidth("20%");
        TOOLBAR.setSpacing(false);

        initButtons();
        initTable();
        initTextArea(botUserRepository.getTotalUsersBot());

        BOT_USER_GRID.setItems(limitBotUserList(BOT_USER_LIST));

        horizontalLayout.add(BOT_USER_GRID);
        verticalLayout.add(TEXT_AREA, TOOLBAR);

        add(horizontalLayout, verticalLayout);
    }


    private void initTextArea(int count) {
        int proportion = Math.round((count* 100) / TOTAL_BOT_USERS);
        TEXT_AREA.setValue(proportion + "% пользователей (" + count + " из " + TOTAL_BOT_USERS + ")");
    }

    private List<BotUser> limitBotUserList(List<BotUser> botUserList) {
        return botUserList.subList(0, botUserList.size());
    }

    private void initButtons() {
        List<Button> buttonList = new ArrayList<>();

        Constants.FILTERS_LIST.forEach(filterName -> {
            buttonList.add(new Button(filterName));
        });


        for (int i = 0; i < buttonList.size(); i++) {
            Button button = buttonList.get(i);
            TOOLBAR.add(button);

            switch (buttonList.get(i).getText()) {
                case Constants.findByNoPublications:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findByNoPublications(LIMIT_BOT_USER_LIST);
                        BOT_USER_GRID.setItems(limitBotUserList(botUserListFiltered));
                        initTextArea(botUserRepository.countFindByNoPublications());
                    });
                    break;
                case Constants.findByPublicationsOver1000:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findByPublicationsOver1000(LIMIT_BOT_USER_LIST);
                        BOT_USER_GRID.setItems(limitBotUserList(botUserListFiltered));
                        initTextArea(botUserRepository.countFindByPublicationsOver1000());
                    });
                    break;
                case Constants.findByNoAvatar:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findByNoAvatar(LIMIT_BOT_USER_LIST);
                        BOT_USER_GRID.setItems(limitBotUserList(botUserListFiltered));
                        initTextArea(botUserRepository.countFindByNoAvatar());
                    });
                    break;
                case Constants.findBySubscriptionsOver1000:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findBySubscriptionsOver1000(LIMIT_BOT_USER_LIST);
                        BOT_USER_GRID.setItems(limitBotUserList(botUserListFiltered));
                        initTextArea(botUserRepository.countFindBySubscriptionsOver1000());
                    });
                    break;
                case Constants.findBySubscriptionsOver2000:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findBySubscriptionsOver2000(LIMIT_BOT_USER_LIST);
                        BOT_USER_GRID.setItems(limitBotUserList(botUserListFiltered));
                        initTextArea(botUserRepository.countFindBySubscriptionsOver2000());
                    });
                    break;
                case Constants.findBySubscriptionsOver3000:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findBySubscriptionsOver3000(LIMIT_BOT_USER_LIST);
                        BOT_USER_GRID.setItems(limitBotUserList(botUserListFiltered));
                        initTextArea(botUserRepository.countFindBySubscriptionsOver3000());
                    });
                    break;
                case Constants.findBySubscribersOver1000:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findBySubscribersOver1000(LIMIT_BOT_USER_LIST);
                        BOT_USER_GRID.setItems(limitBotUserList(botUserListFiltered));
                        initTextArea(botUserRepository.countFindBySubscribersOver1000());
                    });
                    break;
                case Constants.findBySubscribersOver2000:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findBySubscribersOver2000(LIMIT_BOT_USER_LIST);
                        BOT_USER_GRID.setItems(limitBotUserList(botUserListFiltered));
                        initTextArea(botUserRepository.countFindBySubscribersOver2000());
                    });
                    break;
                case Constants.findBySubscribersOver3000:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findBySubscribersOver3000(LIMIT_BOT_USER_LIST);
                        BOT_USER_GRID.setItems(limitBotUserList(botUserListFiltered));
                        initTextArea(botUserRepository.countFindBySubscribersOver3000());
                    });
                    break;
                case Constants.findBySubscriptionsOverSubscribers:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findBySubscriptionsOverSubscribers(LIMIT_BOT_USER_LIST);
                        BOT_USER_GRID.setItems(limitBotUserList(botUserListFiltered));
                        initTextArea( botUserRepository.countFindBySubscriptionsOverSubscribers());
                    });
                    break;
                case Constants.findBySubscribersOverSubscriptions:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findBySubscribersOverSubscriptions(LIMIT_BOT_USER_LIST);
                        BOT_USER_GRID.setItems(limitBotUserList(botUserListFiltered));
                        initTextArea(botUserRepository.countFindBySubscribersOverSubscriptions());
                    });
                    break;
                case Constants.findByIsBusiness:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findByIsBusiness(LIMIT_BOT_USER_LIST);
                        BOT_USER_GRID.setItems(limitBotUserList(botUserListFiltered));
                        initTextArea(botUserRepository.countFindByIsBusiness());
                    });
                    break;
                case Constants.findByIsPrivate:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findByIsPrivate(LIMIT_BOT_USER_LIST);
                        BOT_USER_GRID.setItems(limitBotUserList(botUserListFiltered));
                        initTextArea( botUserRepository.countFindByIsPrivate());
                    });
                    break;
                case Constants.findByPhoneNumberSet:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findByPhoneNumberSet(LIMIT_BOT_USER_LIST);
                        BOT_USER_GRID.setItems(limitBotUserList(botUserListFiltered));
                        initTextArea(botUserRepository.countFindByPhoneNumberSet());
                    });
                    break;
                case Constants.findByLinkSet:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findByLinkSet(LIMIT_BOT_USER_LIST);
                        BOT_USER_GRID.setItems(limitBotUserList(botUserListFiltered));
                        initTextArea(botUserRepository.countFindByLinkSet());
                    });
                    break;
                case Constants.findByEmailSet:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findByEmailSet(LIMIT_BOT_USER_LIST);
                        BOT_USER_GRID.setItems(limitBotUserList(botUserListFiltered));
                        initTextArea(botUserRepository.countFindByEmailSet());
                    });
                    break;
                case Constants.findAll:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findAllLimit(LIMIT_BOT_USER_LIST);
                        BOT_USER_GRID.setItems(limitBotUserList(botUserListFiltered));
                        initTextArea(botUserRepository.getTotalUsersBot());
                    });
                    break;
            }
        }
    }

    private void initTable() {
        Constants.COLUMNS_LIST.forEach(columnId -> BOT_USER_GRID.addColumn(columnId));
        BOT_USER_GRID.getColumns().forEach(column -> {
            column.setWidth("160px");

            switch (column.getKey()) {
                case Constants.USER_NAME:
                    column.setHeader("Имя пользователя");
                    break;
                case Constants.FULL_NAME:
                    column.setHeader("Имя");
                    break;
                case Constants.MEDIA_COUNT:
                    column.setHeader("Публикаций");
                    break;
                case Constants.FOLLOWER_COUNT:
                    column.setHeader("Подписчиков");
                    break;
                case Constants.FOLLOWING_COUNT:
                    column.setHeader("Подписок");
                    break;
                case Constants.IS_PRIVATE:
                    column.setHeader("Закрытый аккаунт");
                    break;
                case Constants.IS_BUSINESS:
                    column.setHeader("Бизнесс аккаунт");
                    break;
                case Constants.EXTERNAL_URL:
                    column.setHeader("Сайт");
                    break;
                case Constants.PUBLIC_PHONE_NUMBER:
                    column.setHeader("Телефон");
                    break;
                case Constants.PUBLIC_EMAIL:
                    column.setHeader("Email");
                    break;
                case Constants.BIOGRAPHY:
                    column.setHeader("Bio");
                    column.setWidth("100%");
                    break;
                case Constants.CATEGORY:
                    column.setHeader("Категория");
                    break;
            }

        });

    }

}
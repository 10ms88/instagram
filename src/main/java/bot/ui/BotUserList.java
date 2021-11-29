package bot.ui;

import bot.config.ApplicationContextProvider;
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

    private final Grid<BotUser> botUserGridGrid = new Grid<>(BotUser.class, false);
    private final VerticalLayout toolbar = new VerticalLayout();
    private final List<BotUser> botUserList = ApplicationContextProvider.getApplicationContext().getBean(BotUserRepository.class).findAll().subList(0,10);
    private List<BotUser> botUserListFiltered = botUserList;
    private final TextArea textArea = new TextArea();

    @Autowired
    public BotUserList() {
        VerticalLayout horizontalLayout = new VerticalLayout();
        horizontalLayout.setWidth("80%");
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidth("20%");
        toolbar.setSpacing(false);

        initButtons();
        initTable();
        initTextArea();

        botUserGridGrid.setItems(botUserList);

        horizontalLayout.add(botUserGridGrid);
        verticalLayout.add(textArea,toolbar);

        add(horizontalLayout, verticalLayout);


    }


    private void initTextArea() {
        int proportion = Math.round((botUserListFiltered.size() * 100) / botUserList.size());
        textArea.setValue(proportion + "% пользователей (" + botUserListFiltered.size() + " из " + botUserList.size() + ")");
    }


    private void initButtons() {
        List<Button> buttonList = new ArrayList<>();
        BotUserRepository botUserRepository = ApplicationContextProvider.getApplicationContext().getBean(BotUserRepository.class);

        Constants.FILTERS_LIST.forEach(filterName -> {
            buttonList.add(new Button(filterName));
        });


        for (int i = 0; i < buttonList.size(); i++) {
            Button button = buttonList.get(i);
            toolbar.add(button);



            switch (buttonList.get(i).getText()) {
                case Constants.findByNoPublications:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findByNoPublications();
                        botUserGridGrid.setItems(botUserListFiltered);
                        initTextArea();
                    });
                    break;
                case Constants.findByPublicationsOver1000:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findByPublicationsOver1000();
                        botUserGridGrid.setItems(botUserListFiltered);
                        initTextArea();
                    });
                    break;
                case Constants.findByNoAvatar:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findByNoAvatar();
                        botUserGridGrid.setItems(botUserListFiltered);
                        initTextArea();
                    });
                    break;
                case Constants.findBySubscriptionsOver1000:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findBySubscriptionsOver1000();
                        botUserGridGrid.setItems(botUserListFiltered);
                        initTextArea();
                    });
                    break;
                case Constants.findBySubscriptionsOver2000:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findBySubscriptionsOver2000();
                        botUserGridGrid.setItems(botUserListFiltered);
                        initTextArea();
                    });
                    break;
                case Constants.findBySubscriptionsOver3000:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findBySubscriptionsOver3000();
                        botUserGridGrid.setItems(botUserListFiltered);
                        initTextArea();
                    });
                    break;
                case Constants.findBySubscribersOver1000:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findBySubscribersOver1000();
                        botUserGridGrid.setItems(botUserListFiltered);
                        initTextArea();
                    });
                    break;
                case Constants.findBySubscribersOver2000:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findBySubscribersOver2000();
                        botUserGridGrid.setItems(botUserListFiltered);
                        initTextArea();
                    });
                    break;
                case Constants.findBySubscribersOver3000:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findBySubscribersOver3000();
                        botUserGridGrid.setItems(botUserListFiltered);
                        initTextArea();
                    });
                    break;
                case Constants.findBySubscriptionsOverSubscribers:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findBySubscriptionsOverSubscribers();
                        botUserGridGrid.setItems(botUserListFiltered);
                        initTextArea();
                    });
                    break;
                case Constants.findBySubscribersOverSubscriptions:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findBySubscribersOverSubscriptions();
                        botUserGridGrid.setItems(botUserListFiltered);
                        initTextArea();
                    });
                    break;
                case Constants.findByIsBusiness:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findByIsBusiness();
                        botUserGridGrid.setItems(botUserListFiltered);
                        initTextArea();
                    });
                    break;
                case Constants.findByIsPrivate:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findByIsPrivate();
                        botUserGridGrid.setItems(botUserListFiltered);
                        initTextArea();
                    });
                    break;
                case Constants.findByPhoneNumberSet:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findByPhoneNumberSet();
                        botUserGridGrid.setItems(botUserListFiltered);
                        initTextArea();
                    });
                    break;
                case Constants.findByLinkSet:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findByLinkSet();
                        botUserGridGrid.setItems(botUserListFiltered);
                        initTextArea();
                    });
                    break;
                case Constants.findByEmailSet:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findByEmailSet();
                        botUserGridGrid.setItems(botUserListFiltered);
                        initTextArea();
                    });
                    break;
                case Constants.findAll:
                    button.addClickListener(e -> {
                        botUserListFiltered = botUserRepository.findAll();
                        botUserGridGrid.setItems(botUserListFiltered);
                        initTextArea();
                    });
                    break;
            }

        }

    }

    private void initTable() {
        Constants.COLUMNS_LIST.forEach(columnId -> botUserGridGrid.addColumn(columnId));
        botUserGridGrid.getColumns().forEach(column -> {
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
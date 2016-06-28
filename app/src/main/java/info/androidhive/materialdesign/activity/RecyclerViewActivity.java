package info.androidhive.materialdesign.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;

public class RecyclerViewActivity extends Activity {

    //private List<Person> persons;

    private List<Bankomat> bankomati;
    private RecyclerView rv;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recyclerview_activity);

        rv = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initializeData() {
//        persons = new ArrayList<>();
//        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.emma));
//        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.lavery));
//        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.lillie));

        bankomati = new ArrayList<>();
        String skopje = "Capitol Mall^41,986038 21,466573~City Mall Скопје^42.004405 21.392426~Ramstor Горно Лисиче ^41,984180 21,483557~АМСМ Дирекција ул.Теодосиј Гологанов бр.51^41,994746 21,421179~Аптека \"Марија\" ул.Водњанска бр.36^41,989650 21,419323~БЕ-КО^41,999418 21,412693~Бензинска пумпа Евроил^42,052370 21,450222~Бензинска пумпа Никол Комерц^42,053836 21,451402~БП Лукоил ^41,944016 21,497742~Бриколаж^41.996380 21.475617~Градежен Институт Македонија - КАТАСТАР ул.Дрезенска бр.52^41,999617 21,406759~ГТЦ Златара Рубин приземје (заден влез макпетрол ) ^41,994275 21,435942~Драмски театар^42,002910 21,408251~ДТЦ Палома Бјанка^41,994969 21,428185~Експозитура Автокоманда^42,001587 21,460264~Експозитура Аеродром^41,986788 21,463268~Експозитура Г.Т.Ц. 2^41,994881 21,434773~Експозитура Г.Т.Ц. кеј 13 Ноември бр.3^41,994850 21,435234~Експозитура Ѓорче Петров^42,006314 21,368307~Експозитура Драчево^41,938554 21,519487~Експозитура Илинден^41,994506 21,577484~Експозитура Капиштец^41,993622 21,411008~Експозитура Карпош 3- Т.Ц. Лептокарија^42,003602 21,397149~Експозитура Карпош 4^42,004999 21,384004~Експозитура Кисела Вода^41,984714 21,438850~Експозитура Клинички Центар^41,990217 21,420793~Експозитура Ново Лисиче^41,981336 21,475429~Експозитура Расадник^41,976098 21,443635~Експозитура Собрание^41,991748 21,431211~Експозитура стар Аеродром^41,986253 21,442894~Експозитура Стара Чаршија^41,999331 21,437557~Експозитура Три Бисери^41,989419 21,459942~Експозитура Улица Македонија^41,995009 21,430814~Експозитура Универзална^41,999131 21,418958~Експозитура Центар^41,996532 21,427606~Експозитура Чаир^42,020783 21,449299~ЕЛ-ДЕ Маркет^42,003150 21,465586~ЕлектроЕлемент^41,995982 21,486239~Зегин^41,998525 21,422477~Комуна^41,991971 21,494017~ЛаПетит Слаткарница^41,974489 21,472055~Лукоил Автокоманда^42,000758 21,461251~Лукоил Аеродром^41,987633 21,473032~Лукоил Камник^42,004353 21,483084~Макоил Зајчев рид^42,015458 21,404324~Макоил Лисиче^41,979658 21,465779~Макпетрол - Козле^41,994220 21,397758~Макпетрол Ѓорче Петров^42,007225 21,334087~Макси Д К.Вода^41,983182 21,439171~Менувачница Фаустина^42,005302 21,416600~МПМ ул Никола бавцаров б.б. ^41,995436 21,429923~Народна Банка^41,993709 21,442444~НЛБ ТБ Дирекција 2^41,992768 21,425310~НЛБ ТБ Дирекција^41,992768 21,425310~НЛБ ТБ Експозитура Сарај ул.\"Сарај\" бб. ^442,004196 21,332238~НЛБ ТБ Соборна^41,999175 21,425676~НЛБ ТБ Ченто ^42,014932 21,521247~Општина Илинден^41.994465 21.575625~Принц Маркет^42,017116 21,436597~Пуцко Петрол - Бензинска Пумпа^42,003247 21,396317~Рамстор^41,992210 21,426468~Рептил Маркет Црниче ^41,983752 21,428838~Салон за мебел СТОЛ^41,989435 21,436339~Салон за убавина Сплендид^42,000606 21,414372~СП Маркет Бутел^42,031048 21,444890~СП Маркет Кисела Вода^41,977249 21,443849~СП Маркет Тафталиџе^42,001015 21,390095~СРЦ Јане Сандански^41,992740 21,465035~Супер Тинекс - ул. Перо Наков бб^41,996197 21,477881~Супер Тинекс Зебра^41,992784 21,419827~Супер Тинекс Раде Кончар^41,981907 21,452819~Супер Тинекс ул.Првомајска бб^41,975143 21,452304~Т.Ц. Беверли Хилс^41,993996 21,416362~Т.Ц. Олимпико^41,998557 21,393595~Тинекс Ѓорче Петров^42,007104 21,361280~Тобако 1 ^41,984929 21,466643~Тобако 2 ^41,987768 21,453231~Тобако ПРЧ ^41,985171 21,467174~Тутунски Комбинат^41,987745 21,437691~ТЦ. Веро^41,99237 21,441575~ул.Орце Николов бр.188 - до МИ-ДА моторс^42,006466 21,407886";

        String niza[] = skopje.split("~");
        for (int i = 0; i < niza.length; i++) {
            String deleno[] = niza[i].split("\\^");
            String name = deleno[0];
            String latlng = deleno[1];

            String niza1[] = latlng.split(" ");
            niza1[0] = niza1[0].replace(",", ".");
            niza1[1] = niza1[1].replace(",", ".");
            double lat = Double.parseDouble(niza1[0]);
            double lng = Double.parseDouble(niza1[1]);
         //  bankomati.add(new Bankomat(name,1,lat,lng));
        }

    }

    private void initializeAdapter() {
        RVAdapter adapter = new RVAdapter(bankomati);
        rv.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "RecyclerView Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://info.androidhive.materialdesign.activity/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "RecyclerView Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://info.androidhive.materialdesign.activity/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}

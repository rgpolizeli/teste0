package com.rgp.goomerlistarango;

import com.google.gson.Gson;
import com.rgp.goomerlistarango.models.Item;
import com.rgp.goomerlistarango.models.Restaurant;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class GsonTypeAdapterUnitTest {

    @Test
    public void deserializeMenuJsonToMenu() throws IOException {
        /*
          Don't pass because original json haven't sales=[];
         */

        String menuJson = "[{\"restaurantId\":1,\"name\":\"Brigadeiro de padaria\",\"image\":\"https://st4.depositphotos.com/1077028/21147/i/1600/depositphotos_211470810-stock-photo-brigadeiro-brazilian-famous-chocolate-candy.jpg\",\"price\":8.0,\"group\":\"Doces\",\"sales\":[{\"description\":\"Terça-feira doce\",\"price\":5.0,\"hours\":[{\"from\":\"09:00\",\"to\":\"18:00\",\"days\":[3]}]}]},{\"restaurantId\":1,\"name\":\"Sonho de chocolate\",\"price\":6.0,\"group\":\"Doces\"},{\"restaurantId\":1,\"name\":\"Brownie\",\"image\":\"https://st.depositphotos.com/1692343/1996/i/950/depositphotos_19965431-stock-photo-fresh-homemade-chocolate-brownie.jpg\",\"price\":4.5,\"group\":\"Doces\",\"sales\":[{\"description\":\"Terça-feira doce\",\"price\":5.0,\"hours\":[{\"from\":\"09:00\",\"to\":\"18:00\",\"days\":[3]}]}]}]";

        Gson gson = new Gson();
        Item[] menu = gson.fromJson(menuJson, Item[].class);
        String serializedJson = gson.toJson(menu);
        assertThat(serializedJson, equalTo(menuJson));

    }

    @Test
    public void convertRestaurantsJsonToListRestaurants() throws IOException {
        /*
          Don't pass because altered order of the last image.
         */

        String restaurantJson = "[{\"id\":1,\"name\":\"Cupcake para todos\",\"address\":\"Rua dos Sonhos, 310\",\"hours\":[{\"from\":\"09:00\",\"to\":\"18:00\",\"days\":[2,3,4,5,6]},{\"from\":\"11:00\",\"to\":\"20:00\",\"days\":[7,1]}],\"image\":\"https://images.unsplash.com/photo-1525640788966-69bdb028aa73?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=d53c30ba55d9ca863d57fabfffdb416b&auto=format&fit=crop&w=1047&q=80\"},{\"id\":2,\"name\":\"Comida de república\",\"address\":\"Rua, 890\",\"hours\":[{\"from\":\"12:00\",\"to\":\"15:00\",\"days\":[2,3,4,5,6]},{\"from\":\"19:00\",\"to\":\"23:00\",\"days\":[2,3,4,5]},{\"from\":\"19:00\",\"to\":\"02:00\",\"days\":[6,7,1]}],\"image\":\"https://images.unsplash.com/photo-1520209268518-aec60b8bb5ca?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=b2b04f771779f9ce281cb3e6035f360e&auto=format&fit=crop&w=884&q=80\"},{\"id\":3,\"name\":\"Comida de mãe\",\"image\":\"https://images.unsplash.com/photo-1527756898251-203e9ce0d9c4?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=6571e314f2e91127c49108ecaabffdaf&auto=format&fit=crop&w=1054&q=80\",\"address\":\"Rua da minha casa, 417\"}]";

        Gson gson = new Gson();
        Restaurant[] restaurants = gson.fromJson(restaurantJson, Restaurant[].class);
        String serializedJson = gson.toJson(restaurants);
        assertThat(serializedJson, equalTo(restaurantJson));

    }
}
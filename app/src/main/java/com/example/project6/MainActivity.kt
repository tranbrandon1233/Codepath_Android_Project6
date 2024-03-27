package com.example.project6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    private lateinit var pokemonList : MutableList<MutableList<String>>
    private lateinit var rvPokemon: RecyclerView
    private lateinit var infoList : MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvPokemon = findViewById(R.id.pokemon_list)
        pokemonList = MutableList(0) { MutableList(0) { "" } }
        infoList = MutableList(0){""}

        getPokemonImageURL()
    }

    private fun getPokemonImageURL() {
        val client = AsyncHttpClient()
        for(j in 0 until 20) {
            client["https://pokeapi.co/api/v2/pokemon/$j", object :
                JsonHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JsonHttpResponseHandler.JSON
                ) {
                    Log.d("Pokemon Success", "$json")
                    val pokemonImgArr = json.jsonObject.getJSONObject("sprites")
                    val abilitiesList = json.jsonObject.getJSONArray("abilities")
                    var abilities = "Abilities: "
                    val name = json.jsonObject.getJSONObject("species").getString("name")
                    infoList.add(pokemonImgArr.getString("front_default"))
                    for (k in 0 until abilitiesList.length()) {

                        abilities += if (k < abilitiesList.length() - 1) {
                            abilitiesList.getJSONObject(k).getJSONObject("ability")
                                .getString("name") + ", "
                        } else {
                            abilitiesList.getJSONObject(k).getJSONObject("ability")
                                .getString("name")
                        }
                    }

                    infoList.add(name.replaceFirstChar { it.titlecase() })
                    infoList.add(abilities)

                    infoList.add(pokemonImgArr.getString(("back_default")))
                    Log.d("infoList","$infoList")
                    pokemonList.add(infoList)
                    infoList = MutableList(0){""}

                    val adapter = PokemonAdapter(pokemonList)
                    rvPokemon.adapter = adapter
                    rvPokemon.layoutManager = LinearLayoutManager(this@MainActivity)
                    rvPokemon.addItemDecoration(
                        DividerItemDecoration(
                            this@MainActivity,
                            LinearLayoutManager.VERTICAL
                        )
                    )
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    throwable: Throwable?
                ) {
                    Log.d("Pokemon Error", errorResponse)
                }
            }]
        }
    }
}
package com.example.myrecipies.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myrecipies.R
import com.example.myrecipies.domain.model.Recipe
import java.util.Date


@Composable
fun RecipiesLayout(viewModel : RecipiesViewModel){

    val state = viewModel.uiState.collectAsState().value

    if (state.isLoading) {
        CircularProgressIndicator()
    } else if (state.errorMessage != null) {
        Text("Error: ${state.errorMessage}")
    } else {
        RecipeGrid(recipes = state.recipes)
    }
}

@Composable
fun RecipeCard(recipe: Recipe) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth() // fill column width in the grid
            .height(300.dp) // fixed height for uniformity
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image
            Image(
                painter = painterResource(id = R.drawable.googlelogo),
                contentDescription = "Recipe image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp) // fixed height for image
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = recipe.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Row of info
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Time Icon",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${recipe.cookTimeMinutes} mins",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.fork_spoon),
                        contentDescription = "Servings Icon",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${recipe.servings} servings",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // pushes content up & balances height
        }
    }
}


@Composable
fun RecipeGrid(recipes: List<Recipe>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 cards per row
        modifier = Modifier.fillMaxSize()
            .padding(6.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = {
            items(items = recipes) { recipe ->
                RecipeCard(recipe)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RecipiesLayoutPreview(){

    val sampleRecipes = listOf(
        Recipe(
            id = 1,
            name = "Spaghetti Carbonara",
            description = "A quick and creamy Italian pasta dish with eggs, cheese, pancetta, and pepper.",
            cookTimeMinutes = 20,
            prepTimeMinutes = 10,
            servings = 2,
            createdAt = Date(),
            updatedAt = null,
            authorId = 101,
            instructions = """
            1. Cook pasta in salted boiling water until al dente.
            2. Fry pancetta until crispy.
            3. Whisk eggs with grated Parmesan and black pepper.
            4. Combine pasta, pancetta, and egg mixture off the heat.
            5. Serve immediately with extra Parmesan and pepper.
        """.trimIndent()
        ),
        Recipe(
            id = 2,
            name = "Chicken Curry",
            description = "A flavorful curry with tender chicken simmered in a spiced tomato and onion gravy.",
            cookTimeMinutes = 40,
            prepTimeMinutes = 15,
            servings = 4,
            createdAt = Date(),
            updatedAt = null,
            authorId = 102,
            instructions = """
            1. Sauté onions, garlic, and ginger until golden.
            2. Add spices and cook until fragrant.
            3. Stir in chicken and sear on all sides.
            4. Add tomatoes and simmer until chicken is cooked through.
            5. Garnish with fresh coriander and serve with rice.
        """.trimIndent()
        ),
        Recipe(
            id = 3,
            name = "Avocado Toast",
            description = "A simple yet delicious breakfast with creamy avocado on crunchy toast.",
            cookTimeMinutes = 5,
            prepTimeMinutes = 5,
            servings = 1,
            createdAt = Date(),
            updatedAt = null,
            authorId = 103,
            instructions = """
            1. Toast bread slices to desired crispiness.
            2. Mash avocado with salt, pepper, and lemon juice.
            3. Spread avocado mixture on toast.
            4. Top with chili flakes, olive oil, or poached egg if desired.
        """.trimIndent()
        ),
        Recipe(
            id = 4,
            name = "Chocolate Chip Cookies",
            description = "Classic chewy cookies loaded with chocolate chips.",
            cookTimeMinutes = 12,
            prepTimeMinutes = 15,
            servings = 24,
            createdAt = Date(),
            updatedAt = null,
            authorId = 104,
            instructions = """
            1. Cream butter and sugars together.
            2. Add eggs and vanilla, mix well.
            3. Stir in flour, baking soda, and salt.
            4. Fold in chocolate chips.
            5. Scoop dough onto baking sheet and bake until golden brown.
        """.trimIndent()
        ),
        Recipe(
            id = 5,
            name = "Greek Salad",
            description = "A refreshing salad with cucumbers, tomatoes, olives, feta, and olive oil.",
            cookTimeMinutes = 0,
            prepTimeMinutes = 15,
            servings = 3,
            createdAt = Date(),
            updatedAt = null,
            authorId = 105,
            instructions = """
            1. Chop cucumbers, tomatoes, and red onion.
            2. Combine with olives and crumbled feta.
            3. Drizzle with olive oil and oregano.
            4. Toss gently and serve chilled.
        """.trimIndent()
        )
    )
    RecipeGrid(recipes = sampleRecipes)
}
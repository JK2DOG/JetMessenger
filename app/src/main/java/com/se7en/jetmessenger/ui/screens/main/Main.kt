package com.se7en.jetmessenger.ui.screens.main

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.zsoltk.compose.router.BackStack
import com.github.zsoltk.compose.router.Router
import com.se7en.jetmessenger.data.me
import com.se7en.jetmessenger.data.models.User
import com.se7en.jetmessenger.ui.Routing
import com.se7en.jetmessenger.ui.components.MainBottomNav
import com.se7en.jetmessenger.ui.components.MainTopBar

@Composable
fun Routing.Root.Main.Content(
    defaultRouting: Routing.BottomNav,
    onChatClick: (user: User) -> Unit,
    onSearchClick: () -> Unit
) {
    Router(defaultRouting = defaultRouting) { backStack ->
        Scaffold(
            topBar = {
                MainTopBar(
                    me.picture.thumbnail,
                    currentRouting = backStack.last(),
                    onActionClick = { }
                )
            },
            bottomBar = {
                MainBottomNav(
                    listOf(Routing.BottomNav.Chats, Routing.BottomNav.People),
                    currentRouting = backStack.last(),
                    onSelected = { onBottomNavItemSelected(it, backStack) }
                )
            }
        ) { innerPadding ->
            Surface(
                modifier = Modifier.padding(innerPadding),
                color = MaterialTheme.colors.surface
            ) {
                Crossfade(current = backStack.last()) { routing ->
                    when(routing) {
                        is Routing.BottomNav.Chats -> routing.Content(onChatClick, onSearchClick)
                        is Routing.BottomNav.People -> routing.Content(onChatClick, onSearchClick)
                    }
                }
            }
        }
    }
}

fun onBottomNavItemSelected(routing: Routing.BottomNav, backStack: BackStack<Routing.BottomNav>) {
    when(routing) {
        is Routing.BottomNav.Chats ->
            if(backStack.last() !is Routing.BottomNav.Chats) {
                backStack.newRoot(routing)
            }
        is Routing.BottomNav.People ->
            if(backStack.last() !is Routing.BottomNav.People) {
                backStack.push(routing)
            }
    }
}

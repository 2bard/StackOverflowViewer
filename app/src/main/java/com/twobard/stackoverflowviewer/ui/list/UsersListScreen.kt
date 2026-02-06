package com.twobard.stackoverflowviewer.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.twobard.stackoverflowviewer.data.dto.toUser
import com.twobard.stackoverflowviewer.domain.user.User
import com.twobard.stackoverflowviewer.ui.theme.paddingMedium
import com.twobard.stackoverflowviewer.ui.theme.paddingSmall
import com.twobard.stackoverflowviewer.ui.utils.Utils.Companion.randomUser


class UserListPreviewProvider : PreviewParameterProvider<List<User>> {
    override val values = sequenceOf(
        emptyList(),
        List(6) { randomUser().toUser() }
    )
}

class UserPreviewProvider : PreviewParameterProvider<User> {
    override val values = sequenceOf(
        randomUser().toUser()
    )
}

@Preview
@Composable
fun UsersListScreen(@PreviewParameter(UserListPreviewProvider::class) users: List<User>,) {


    LazyColumn(

    ) {

        items(
            key = { users[it].id },
            count = users.size
        ) {
            UserCard(users[it])
        }
    }
}

@Preview
@Composable
fun UserCard(@PreviewParameter(UserPreviewProvider::class) user: User){

    Card(modifier = Modifier.padding(paddingSmall), elevation = CardDefaults.cardElevation()) {
        Row(modifier = Modifier.padding(paddingMedium)){

            Column(modifier = Modifier.weight(1f)) {


                Row {
                    Text(user.displayName)
                }

                Row {
                    Text("Reputation")
                    Text(user.reputation.toString())
                }


            }


        }
    }
}
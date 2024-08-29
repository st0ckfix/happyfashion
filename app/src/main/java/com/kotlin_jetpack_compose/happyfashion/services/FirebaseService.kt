package com.kotlin_jetpack_compose.happyfashion.services

import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.database
import com.kotlin_jetpack_compose.happyfashion.models.PostContent
import com.kotlin_jetpack_compose.happyfashion.models.PostDetail
import com.kotlin_jetpack_compose.happyfashion.models.PostHeader
import com.kotlin_jetpack_compose.happyfashion.models.PostModel
import com.kotlin_jetpack_compose.happyfashion.models.PostRule
import com.kotlin_jetpack_compose.happyfashion.models.PostUser
import com.kotlin_jetpack_compose.happyfashion.models.UserModel
import kotlinx.coroutines.tasks.await

const val URL = "https://happyfashion-af31a-default-rtdb.asia-southeast1.firebasedatabase.app/"
const val currentUser = "current_user"

object FirebaseService {
    private val firebase = Firebase.database(URL).getReference()

    private var listFollowing = emptyList<String>()
    suspend fun getListFollowing() : List<String> {
        if(listFollowing.isEmpty()) fetchListFollowing()
        return listFollowing
    }
    private suspend fun fetchListFollowing(){
        listFollowing = firebase.child("user/$currentUser/following").orderByKey().get().await().children.mapNotNull {
            it.getValue(String::class.java)
        }
    }
    private suspend fun fetchFollowingData(uid: String) : UserModel = firebase.child("user/$uid").get().await().toUser()


    private var mapUser = emptyMap<String, UserModel>()
    suspend fun getUser(uid: String) : UserModel{
        if(mapUser.containsKey(uid)) return mapUser[uid]!!
        else{
            mapUser += uid to fetchFollowingData(uid)
            return mapUser[uid]!!
        }
    }


    private var listPost = mutableListOf<String>()
    suspend fun getListPost() : List<String> {
        if(listPost.isEmpty()) fetchListPost()
        return listPost
    }
    private suspend fun fetchListPost(){
        getListFollowing().forEach {
            Firebase.database(URL).getReference().child("user/$it/posts").addChildEventListener(object :
                ChildEventListener {
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        listPost.add(snapshot.getValue(String::class.java) !!)
                        listPost.sortDescending()
                    }
                    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
                    override fun onChildRemoved(snapshot: DataSnapshot) {}
                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                    override fun onCancelled(error: DatabaseError) {}
                }
            )
        }
    }
    suspend fun fetchPostData(key: String) : PostModel = firebase.child("post/$key").get().await().toPost()

    private fun DataSnapshot.toUser() = UserModel(
        id = this.child("id").value.toString(),
        name = this.child("name").value.toString(),
        image = this.child("image").value.toString()
    )

    private fun DataSnapshot.toPost() = PostModel(
        key = this.child("key").value.toString(),
        postUser = this.child("post_user").getValue(PostUser::class.java)!!,
        postRule = this.child("post_rule").getValue(PostRule::class.java),
        postHeader = this.child("post_header").getValue(PostHeader::class.java),
        postContent = this.child("post_content").getValue(PostContent::class.java)!!,
        postDetail = this.child("post_detail").getValue(PostDetail::class.java)!!,
        timestamp = this.child("timestamp").value as Long
    )

}

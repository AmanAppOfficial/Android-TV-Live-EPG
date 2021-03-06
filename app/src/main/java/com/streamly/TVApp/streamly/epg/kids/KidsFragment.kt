package com.streamly.TVApp.streamly.epg.kids

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Spanned
import android.text.SpannedString
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.streamly.TVApp.streamly.*
import com.streamly.TVApp.streamly.entity.ProgramGuideChannel
import com.streamly.TVApp.streamly.entity.ProgramGuideSchedule
import com.streamly.TVApp.streamly.epg.Datum
import com.streamly.TVApp.streamly.local_storage.TokenStorage
import com.streamly.TVApp.streamly.log_in.LogInActivity
import com.streamly.TVApp.streamly.playervideo.PlayerActivity
import com.streamly.TVApp.streamly.retrofit_clients.ApiClient
import com.streamly.TVApp.streamly.retrofit_clients.ApiInterface
import com.streamly.TVApp.streamly.utilities.CommonUtility
import com.streamly.TVApp.streamly.utilities.ConstantUtility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoUnit
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList
import kotlin.random.Random

class KidsFragment(context: Context, dataListm: MutableList<Datum>): ProgramGuideFragment<KidsFragment.SimpleProgram>() {
//class EpgFragment (context: Context): ProgramGuideFragment<EpgFragment.SimpleProgram>() {


    val eContext=context
    val list =dataListm
    val channels = ArrayList<SimpleChannel>()
    lateinit var MIN_CHANNEL_START_TIME: ZonedDateTime
    lateinit var MAX_CHANNEL_START_TIME: ZonedDateTime
    lateinit var MIN_CHANNEL_END_TIME: ZonedDateTime
    lateinit var MAX_CHANNEL_END_TIME: ZonedDateTime
    val MIN_SHOW_LENGTH_SECONDS = TimeUnit.MINUTES.toSeconds(5)
    val MAX_SHOW_LENGTH_SECONDS = TimeUnit.MINUTES.toSeconds(120)
    lateinit var localDate: LocalDate
    val channel_showName_list = ArrayList<String>()
    val showName_list = ArrayList<String>()
    var id_matched: Int? = 0
    var verticalBean = VerticalHomeBean()
    var homeBean = HomeBean()
    var homelist = ArrayList<HomeBean>()
    var verticallist = ArrayList<VerticalHomeBean>()


    companion object {
        private val TAG = KidsFragment::class.java.name
    }



    data class SimpleChannel(
            override val id: String,
            override val name: Spanned?,
            override val imageUrl: String?,
            val videoUrl: String?
    ) : ProgramGuideChannel

    // You can put your own data in the program class
    data class SimpleProgram(
            val id: String,
            val description: String,
            val metadata: String,
            val playUrl: String
    )


    //#neovifyissueresolved28sep
    override fun onScheduleClicked(programGuideSchedule: ProgramGuideSchedule<SimpleProgram>) {
        val innerSchedule = programGuideSchedule.program
        if (innerSchedule == null) {
            // If this happens, then our data source gives partial info
            Log.w(TAG, "Unable to open schedule: $innerSchedule")
            return
        }
        if (programGuideSchedule.isCurrentProgram) {
            Log.e(TAG, "::::>>>if: \nprogramGuideSchedule.program: " + programGuideSchedule.program
                    + "\nid: " + programGuideSchedule.id
                    + "\ndisplaytitle : " + programGuideSchedule.displayTitle)

            /*for (i in 0 until channel_showName_list.size) {
               for (j in 0 until channels.size) {
                   if (channel_showName_list[i].equals(channels.get(j).id)) {

                       if (id_matched == 0) {
                           Log.e(
                                   TAG, "::::>>>if: \nchannelname: " + channels[j].name
                                   + "\nid: " + channels[j].id
                                   + "\nvideourl : " + channels[j].videoUrl
                                   + "\n\n csl: " + channel_showName_list[i]
                           )
                           id_matched = 1

                           val intent1 = Intent(activity, PlayerActivity::class.java)
                           intent1.putExtra("from", channels.get(j).videoUrl)
                           startActivity(intent1)

                       }
                   }
               }
           }*/

            val intent1 = Intent(activity, PlayerActivity::class.java)
            intent1.putExtra("from", innerSchedule.playUrl)
            startActivity(intent1)

            Log.e(TAG, "Open live player " + innerSchedule.metadata + "<<>>" + innerSchedule.playUrl
                    + "<<>>" + innerSchedule.id)
        } else {
            Log.e(TAG, "Open detail page " + innerSchedule.metadata + "<<>>" + innerSchedule.playUrl
                    + "<<>>" + innerSchedule.id)


        }
    }

    override fun onResume() {
        super.onResume()
        id_matched = 0
    }

    override fun onScheduleSelected(programGuideSchedule: ProgramGuideSchedule<SimpleProgram>?) {
        val titleView = view?.findViewById<TextView>(R.id.programguide_detail_title)
        titleView?.text = programGuideSchedule?.displayTitle
        val metadataView = view?.findViewById<TextView>(R.id.programguide_detail_metadata)
        metadataView?.text = programGuideSchedule?.program?.metadata
        val descriptionView = view?.findViewById<TextView>(R.id.programguide_detail_description)
        descriptionView?.text = programGuideSchedule?.program?.description
        val imageView = view?.findViewById<ImageView>(R.id.programguide_detail_image) ?: return
        if (programGuideSchedule != null) {
            Glide.with(imageView)
                    .load("https://picsum.photos/462/240?random=" + programGuideSchedule.displayTitle.hashCode())
                    .centerCrop()
                    .error(R.drawable.programguide_icon_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)
        } else {
            Glide.with(imageView).clear(imageView)
        }

    }

    override fun isTopMenuVisible(): Boolean {
        return false
    }

    @SuppressLint("CheckResult")
    override fun requestingProgramGuideFor(localDate: LocalDate) {
        this.localDate=localDate
        // Faking an asynchronous loading here

        this.localDate = localDate
        // Faking an asynchronous loading here
        setState(State.Loading)

        MIN_CHANNEL_START_TIME = localDate.atStartOfDay().minusDays(1).withHour(2).truncatedTo(ChronoUnit.HOURS).atZone(DISPLAY_TIMEZONE)
        MAX_CHANNEL_START_TIME = localDate.atStartOfDay().minusDays(1).withHour(8).truncatedTo(ChronoUnit.HOURS).atZone(DISPLAY_TIMEZONE)
        MIN_CHANNEL_END_TIME = localDate.atStartOfDay().plusDays(1).withHour(21).truncatedTo(ChronoUnit.HOURS).atZone(DISPLAY_TIMEZONE)
        MAX_CHANNEL_END_TIME = localDate.atStartOfDay().plusDays(2).withHour(4).truncatedTo(ChronoUnit.HOURS).atZone(DISPLAY_TIMEZONE)

        /* MIN_CHANNEL_START_TIME = localDate.atStartOfDay().withHour(2).truncatedTo(ChronoUnit.HOURS).atZone(DISPLAY_TIMEZONE)
         MAX_CHANNEL_START_TIME = localDate.atStartOfDay().withHour(8).truncatedTo(ChronoUnit.HOURS).atZone(DISPLAY_TIMEZONE)
         MIN_CHANNEL_END_TIME = localDate.atStartOfDay().withHour(23).truncatedTo(ChronoUnit.HOURS).atZone(DISPLAY_TIMEZONE)
         MAX_CHANNEL_END_TIME = localDate.plusDays(1).atStartOfDay().withHour(8).truncatedTo(ChronoUnit.HOURS).atZone(DISPLAY_TIMEZONE)*/

       val authToken = TokenStorage.readSharedToken(eContext, ConstantUtility.AUTH_TOKEN, null)
        getFirstApi(authToken)
        getSecondApi(authToken)

    }

    @SuppressLint("CheckResult")
    private fun getFirstApi(authToken: String) {
//        /*for (i in 0 until data.size) {
//            channels.add(SimpleChannel(data[i].id.toString(),SpannedString(data[i].callSign),data[i].logo,data[i].url))
//        }*/
//        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
//        apiInterface.getChannelGenre2("Bearer $authToken")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    //success
//
//                    it.data.kids.forEach {
//                        channels.add(SimpleChannel(it.id.toString(),SpannedString(it.callSign),it.logo,it.url))
//                    }
//
//                    Log.e("### kidsList",it.data.kids.size.toString())
//
//                },{
//                    //error
//                    if (it.toString().contains("401")){
//                        CommonUtility.showToast(context, ConstantUtility.SESSION_EXPIRED)
//                        activity!!.startActivity(Intent(activity, LogInActivity::class.java))
//                        (activity as Activity).finish()
//                    }
//                })

        list.forEach {
             channels.add(SimpleChannel(it.id.toString(),SpannedString(it.callSign),it.logo,it.url))
        }

    }

    //#neovifyissueresolved28sep
    @SuppressLint("CheckResult")
    private fun getSecondApi(authToken: String) {
        var dayss: Long ?= 1
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        apiInterface.getScheduleList("Bearer $authToken").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                   .subscribe(
                           {
                    //success
                           val showNames = ArrayList<String>()
//                           for(i in 0..it.data.size-1){
                               Log.e("### episodeData",it.data.size.toString())

                               //#neovifyissueresolved28sep
                               val p: Pattern = Pattern.compile("\\d+")
                               val m: Matcher = p.matcher(it.message)
                               while (m.find()) {
                                   Log.e(TAG, "::>>>digit: " + m.group())
                                   dayss = (m.group()).toLong()
                               }

                               //#neovifyissueresolved28sep
                               for (i in 0 until it.data.size) {
                                   verticalBean = VerticalHomeBean()

//                                   Log.e("###$TAG", "insideIT "+it.data[i].callSign.toString())
                                   verticalBean.headerTitle = it.data[i].callSign
                                   homelist = ArrayList()

                                   it.data[i].programs.forEach {
                                       homeBean = HomeBean()
                                       showNames.add(it.title)
                                       showName_list.add(it.title)

                                       val st = Instant.ofEpochMilli((it.start.toString() + "000").toLong())
                                       val et = Instant.ofEpochMilli((it.end.toString() + "000").toLong())

                                       homeBean.title = it.title
                                       homeBean.description = it.description
                                       homeBean.start_time = st
                                       homeBean.end_time = et
                                       homelist.add(homeBean)
                                   }

                                   verticalBean.allItemsInSection = homelist
                                   channel_showName_list.add(it.data[i].getmId())
                                   verticallist.add(verticalBean)
                               }


                               val channelMap = mutableMapOf<String, List<ProgramGuideSchedule<SimpleProgram>>>()

                       var k = 0
                       Log.e("### verticalList ","$k <ss>"+ channels.size)

                             verticallist.forEach  first@ { channel ->
//                           Log.e("###${TAG}", "outSideIT " + channel)

                                       if (k<channels.size) {
                                       val scheduleList = mutableListOf<ProgramGuideSchedule<SimpleProgram>>()
                                       var nextTime = randomTimeBetween(MIN_CHANNEL_START_TIME, MAX_CHANNEL_START_TIME)

//                            for (p in 0 until verticallist[k].allItemsInSection!!.size) {
                                       while (nextTime.isBefore(MIN_CHANNEL_END_TIME)) {
                                           Log.e("### allItemsInSection1 ", "" + verticallist[k].allItemsInSection.size)
                                           for (p in 0 until verticallist[k].allItemsInSection!!.size) {


                                               val endTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(nextTime.toEpochSecond() + Random.nextLong(MIN_SHOW_LENGTH_SECONDS, MAX_SHOW_LENGTH_SECONDS)), ZoneOffset.UTC)


                                               val schedule = createSchedule(
                                                       channels.get(k),
                                                       verticallist[k].allItemsInSection!![p].title!!,
//                                           nextTime,
//                                           endTime,
                                                       verticallist[k].allItemsInSection!![p]!!.start_time!!,
                                                       verticallist[k].allItemsInSection!![p]!!.end_time!!,
                                                       verticallist[k].allItemsInSection!![p].description!!,
                                                       channels.get(k).videoUrl.toString())
                                               scheduleList.add(schedule)
                                               nextTime = endTime

//                                }

                                               val endTimee =
                                                       if (nextTime.isBefore(MAX_CHANNEL_END_TIME)) randomTimeBetween(nextTime, MAX_CHANNEL_END_TIME)
                                                       else MAX_CHANNEL_END_TIME

                                               val finalSchedule = createSchedule(
                                                       channels.get(k),
                                                       verticallist[k].allItemsInSection!![p].title!!,
//                                           nextTime,
//                                           endTime,
                                                       verticallist[k].allItemsInSection!![p]!!.start_time!!,
                                                       verticallist[k].allItemsInSection!![p]!!.end_time!!,
                                                       verticallist[k].allItemsInSection!![p].description!!,
                                                       channels.get(k).videoUrl.toString())
                                               channelMap.put(channels.get(k).id, scheduleList)

                                           }

                                       }

                                   }else{
                                           Log.e("### $TAG else ","$k <ss>"+ channels.size)
                                           return@first
                                       }

                                       k += 1
                               }
                       val pair = Pair(channels, channelMap)
                       setData(pair.first, pair.second, localDate, dayss!!)
                       setState(State.Content)

                   },
                           {
                    //error
                    Log.e("###$TAG","error "+it.message)
                    Log.e("###$TAG","error "+it.localizedMessage)
                })
    }


    //#neovifyissueresolved28sep
    private fun createSchedule(
            channel: SimpleChannel,
            scheduleName: String,
            startTime: Instant,
            endTime: Instant,
//            startTime: ZonedDateTime,
//            endTime: ZonedDateTime,
            description: String,
            playUrl: String
    ): ProgramGuideSchedule<SimpleProgram> {
        val id = Random.nextLong(100_000L)
//        val metadata = DateTimeFormatter.ofPattern("'Starts at' HH:mm").format(startTime)
        val metadata = getDate(startTime.toEpochMilli(), "'Starts at' HH:mm")
        return ProgramGuideSchedule.createScheduleWithProgram(id, startTime, endTime, true, scheduleName,
//        return ProgramGuideSchedule.createScheduleWithProgram(id, startTime.toInstant(), endTime.toInstant(), true, scheduleName,
                SimpleProgram(id.toString(), description,
                        metadata+"",
                        playUrl))

    }

    /**
     * Return date in specified format.
     * @param milliSeconds Date in milliseconds
     * @param dateFormat Date format
     * @return String representing date in specified format
     */
    fun getDate(milliSeconds: Long, dateFormat: String?): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(milliSeconds)
        return formatter.format(calendar.getTime())
    }

    private fun randomTimeBetween(min: ZonedDateTime, max: ZonedDateTime): ZonedDateTime {
        val randomEpoch = Random.nextLong(min.toEpochSecond(), max.toEpochSecond())
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(randomEpoch), ZoneOffset.UTC)
    }

    override fun requestRefresh() {
        // You can refresh other data here as well.
        requestingProgramGuideFor(currentDate)
    }
}
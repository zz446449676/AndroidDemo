import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class test(val name:String, val age: Int): ActivityCompat(), CoroutineContext {
    var context = this;
    suspend fun test() {
        GlobalScope.launch {  }
        coroutineScope {
            launch(Dispatchers.Main) {  }
        }
        coroutineScope.launch() {  }
    }
    val coroutineScope = CoroutineScope(context);
    override fun <R> fold(initial: R, operation: (R, CoroutineContext.Element) -> R): R {
        TODO("Not yet implemented")
    }

    override fun <E : CoroutineContext.Element> get(key: CoroutineContext.Key<E>): E? {
        TODO("Not yet implemented")
    }

    override fun minusKey(key: CoroutineContext.Key<*>): CoroutineContext {
        TODO("Not yet implemented")
    }

}
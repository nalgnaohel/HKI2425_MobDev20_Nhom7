import com.example.harmonyhub.data.network.*
import com.example.harmonyhub.data.network.Response
import com.example.harmonyhub.data.repository.DefaultHomeScreenRepo
import kotlinx.coroutines.runBlocking
import okhttp3.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.IOException

class DefaultHomeScreenRepoTest {

    private lateinit var repo: DefaultHomeScreenRepo

    @Before
    fun setUp() {
        repo = DefaultHomeScreenRepo()
    }

    @Test
    fun `test updatePopularItem success`() = runBlocking {
        var result = repo.updatePopularItem()
        assertNotNull(result)
        assertEquals(result!!.listPopularAlbums!!.size, 9)
        assertEquals(result!!.listPopularArtist!!.size, 10)
        assertEquals(result!!.listChart!!.size, 6)
    }
}

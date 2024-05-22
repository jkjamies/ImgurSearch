import com.jkjamies.imgur.api.domain.models.ImgurSearchResult
import com.jkjamies.imgur.api.domain.repository.ImgurRepository
import com.jkjamies.imgur.api.domain.usecase.GetImgurByIdUseCase
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf

class GetImgurByIdUseCaseTest : StringSpec({

    "GetImgurByIdUseCase should return ImgurSearchResult" {
        // Given
        val mockRepository = mockk<ImgurRepository>()
        val mockImgurSearchResult =
            ImgurSearchResult(
                id = "123",
                title = "Mock Imgur",
                link = "https://example.com",
                imageType = "jpg",
            )

        val expectedResults = flowOf(Result.success(mockImgurSearchResult))

        coEvery { mockRepository.getImgurImageById(any()) } returns expectedResults

        // When
        val useCase = GetImgurByIdUseCase(mockRepository)
        val result = useCase("123")

        // Then
        result shouldBe expectedResults
    }
})

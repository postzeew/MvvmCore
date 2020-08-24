package com.postzeew.mvvmcore

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.Dimension.SP
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import kotlinx.android.synthetic.main.view_screen_state.view.*

class ScreenStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var state: State = State.None
        set(value) {
            field = value
            onStateChanged()
        }

    lateinit var onRetryClickListener: (() -> Unit)

    init {
        View.inflate(context, R.layout.view_screen_state, this)

        MvvmCore.screenStateViewConfig?.let(::applyConfig)

        retryButton.setOnClickListener {
            onRetryClickListener.invoke()
        }
    }

    private fun onStateChanged() {
        when (val localState = state) {
            State.None -> gone()
            State.Loading -> {
                errorLl.gone()
                loadingFl.show()
                show()
            }
            is State.Error -> {
                loadingFl.gone()

                val errorInfo = MvvmCore.errorInfoResolver.resolveErrorInfo(localState.throwable)
                errorTitleTv.text = errorInfo.title
                errorDescriptionTv.text = errorInfo.description
                errorLl.show()

                show()
            }
        }
    }

    private fun applyConfig(config: Config) {
        config.titleConfig?.let { titleConfig ->
            applyTextConfig(errorTitleTv, titleConfig)
        }

        config.descriptionConfig?.let { descriptionConfig ->
            applyTextConfig(errorDescriptionTv, descriptionConfig)
        }

        config.buttonConfig?.let { buttonConfig ->
            applyTextConfig(retryButton, buttonConfig)

            with(buttonConfig) {
                text?.let { text ->
                    retryButton.text = text
                }
                backgroundColor?.let { backgroundColor ->
                    retryButton.backgroundTintList = ColorStateList.valueOf(backgroundColor)
                }
            }
        }

        config.loaderConfig?.color?.let { color ->
            DrawableCompat.setTint(progressBar.indeterminateDrawable, color)
        }
    }

    private fun applyTextConfig(textView: TextView, textConfig: Config.TextConfig) {
        with(textConfig) {
            fontResId?.let { fontResId ->
                textView.typeface = ResourcesCompat.getFont(context, fontResId)
            }
            textSize?.let { textSize ->
                textView.textSize = textSize
            }
            textColor?.let { textColor ->
                textView.setTextColor(textColor)
            }
            isTextBold?.let { isTextBold ->
                textView.typeface = Typeface.create(
                    textView.typeface,
                    if (isTextBold) Typeface.BOLD else Typeface.NORMAL
                )
            }
        }
    }

    sealed class State {

        object None : State()

        object Loading : State()

        data class Error(
            val throwable: Throwable
        ) : State()
    }

    data class Config(
        val titleConfig: TextConfig? = null,
        val descriptionConfig: TextConfig? = null,
        val buttonConfig: ButtonConfig? = null,
        val loaderConfig: LoaderConfig? = null

    ) {
        open class TextConfig(
            @FontRes open val fontResId: Int? = null,
            @Dimension(unit = SP) open val textSize: Float? = null,
            @ColorInt open val textColor: Int? = null,
            open val isTextBold: Boolean? = null
        )

        data class ButtonConfig(
            @FontRes override val fontResId: Int? = null,
            @Dimension(unit = SP) override val textSize: Float? = null,
            @ColorInt override val textColor: Int? = null,
            override val isTextBold: Boolean? = null,
            val text: String? = null,
            @ColorInt val backgroundColor: Int? = null
        ) : TextConfig(fontResId, textSize, textColor, isTextBold)

        data class LoaderConfig(
            @ColorInt val color: Int? = null
        )
    }
}
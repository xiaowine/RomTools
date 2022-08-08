package cn.xiaowine.romtools.fragment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import cn.xiaowine.romtools.R
import cn.xiaowine.romtools.databinding.FragmentRootBinding
import cn.xiaowine.ui.dialog.MdProgressDialog
import cn.xiaowine.utils.ActivityUtils
import cn.xiaowine.utils.Utils
import cn.xiaowine.utils.Utils.TAG
import com.jaredrummler.ktsh.Shell
import java.io.File
import java.io.InputStream


class RootFragment : Fragment(), View.OnClickListener {

    private lateinit var register: ActivityResultLauncher<Array<String>>
    private lateinit var fragmentActivity: FragmentActivity
    private var _binding: FragmentRootBinding? = null

    private val binding get() = _binding!!
    private val type = "boot"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentActivity = requireActivity()
        register = Utils.registerResul(this, fragmentActivity, type)
        _binding = FragmentRootBinding.inflate(inflater, container, false)
        binding.root.setOnClickListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.flashRoot.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.flash_root -> {
                val dialogCp = MdProgressDialog(fragmentActivity).setTitle(fragmentActivity.getString(R.string.root_ing)).setMessages(fragmentActivity.getString(R.string.no_exit)).show()
                Thread {
                    val fileList: Array<String> = fragmentActivity.assets.list("")!!
                    for (element in fileList) {
                        try {
                            val inputStream: InputStream = fragmentActivity.assets.open(element)
                            val file = File(("${fragmentActivity.cacheDir.absolutePath}/${element}"))
                            if (!file.exists()) {
                                file.writeBytes(inputStream.readBytes())
                            }
                        } catch (_: Exception) {
                        }
                    }
                    val command = "mv ${fragmentActivity.cacheDir.absolutePath} /data/cache  && cd /data/cache/cache/ && chmod 777 /data/cache/cache/* && dd if=/dev/block/by-name/boot of=/data/cache/cache/boot.img  && ./boot_patch.sh boot.img"
                    val result = Shell("sh").run(command)
                    if (result.exitCode == 0) {
                        val su = Shell("sh")
                        val a = su.run("dd if=/data/cache/cache/new-boot.img of=/dev/block/by-name/boot")
                        Log.i(TAG, a.output())
                        dialogCp.dismiss()
                        if (a.isSuccess) {
                            ActivityUtils.showToastOnLooper(fragmentActivity, fragmentActivity.getString(R.string.root_done))
                            return@Thread
                        }
                    }
                    ActivityUtils.showToastOnLooper(fragmentActivity, fragmentActivity.getString(R.string.root_error))
                }.start()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
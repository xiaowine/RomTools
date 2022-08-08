package cn.xiaowine.romtools.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import cn.xiaowine.romtools.R
import cn.xiaowine.romtools.databinding.FragmentBootBinding
import cn.xiaowine.utils.Utils


class BootFragment : Fragment(), View.OnClickListener {

    private lateinit var register: ActivityResultLauncher<Array<String>>
    private lateinit var fragmentActivity: FragmentActivity
    private var _binding: FragmentBootBinding? = null

    private val binding get() = _binding!!
    private val type = "boot"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentActivity = requireActivity()
        register = Utils.registerResul(this, fragmentActivity, type)
        _binding = FragmentBootBinding.inflate(inflater, container, false)
        binding.root.setOnClickListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backupBoot.setOnClickListener(this)
        binding.flashBoot.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.backup_boot -> Utils.backUp(fragmentActivity, type)


            R.id.flash_boot -> register.launch(arrayOf("application/*"))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
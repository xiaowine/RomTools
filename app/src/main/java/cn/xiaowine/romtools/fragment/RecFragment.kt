package cn.xiaowine.romtools.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import cn.xiaowine.romtools.R
import cn.xiaowine.romtools.databinding.FragmentRecBinding
import cn.xiaowine.utils.Utils


class RecFragment : Fragment(), View.OnClickListener {

    private lateinit var register: ActivityResultLauncher<Array<String>>
    private lateinit var fragmentActivity: FragmentActivity
    private var _binding: FragmentRecBinding? = null

    private val binding get() = _binding!!
    private val type = "recovery"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentActivity = requireActivity()
        register = Utils.registerResul(this, fragmentActivity, type)
        _binding = FragmentRecBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backupRec.setOnClickListener(this)
        binding.flashRec.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.backup_rec -> Utils.backUp(fragmentActivity, type)


            R.id.flash_rec -> register.launch(arrayOf("application/*"))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package cn.xiaowine.romtools.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import cn.xiaowine.romtools.R
import cn.xiaowine.romtools.databinding.FragmentMainBinding


class MainFragment : Fragment(), View.OnClickListener {

    private lateinit var fragmentActivity: FragmentActivity
    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentActivity = requireActivity()
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.roots.setOnClickListener(this)
        binding.recovery.setOnClickListener(this)
        binding.boot.setOnClickListener(this)
        binding.logo.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
//            R.id.roots -> ActivityUtils.showToastOnLooper(fragmentActivity, getString(R.string.todo))
            R.id.roots -> findNavController().navigate(R.id.action_MainFragment_to_RootFragment)
            R.id.recovery -> findNavController().navigate(R.id.action_MainFragment_to_RecFragment)
            R.id.boot -> findNavController().navigate(R.id.action_MainFragment_to_BootFragment)
            R.id.logo -> findNavController().navigate(R.id.action_MainFragment_to_LogoFragment)
        }
    }
}
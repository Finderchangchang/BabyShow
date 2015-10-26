package liuliu.babyshow.activity;

import net.tsz.afinal.annotation.view.CodeNote;

import liuliu.babyshow.R;
import liuliu.babyshow.base.BaseActivity;
import liuliu.custom.control.edittext.ImageEditText;

public class RegUserActivity extends BaseActivity {
    @CodeNote(id = R.id.tel_et_reg_user)
    ImageEditText tel_et;
    @CodeNote(id = R.id.code_et_reg_user)
    ImageEditText code_et;
    String tel;//手机号码
    String code;//验证码

    @Override
    public void initViews() {
        setContentView(R.layout.activity_reg_user);
    }

    @Override
    public void initEvents() {

    }
}

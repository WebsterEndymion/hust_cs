package com.team429.hometeacher.ui.question;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import com.team429.hometeacher.R;
import com.team429.hometeacher.data.RuntimeData;
import com.team429.hometeacher.question.Question;
import com.team429.hometeacher.question.QuestionProvider;

/**
 * 这是片段布局fragment_question的业务逻辑，用于显示问题
 * @author zhanghang
 */

public class QuestionFragment extends Fragment {

    private QuestionViewModel mViewModel;

    // 跳过按钮
    private Button nextButton;
    // 提交按钮
    private Button submitButton;
    // 答案文本框
    private TextInputEditText answerTextEdit;
    // 问题标签
    private TextView questionText;
    // 问题
    private Question question;
    // 问题生成器
    private QuestionProvider questionProvider;

    public static QuestionFragment newInstance() {
        return new QuestionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_question, container, false);
        // 连接UI与逻辑
        nextButton = view.findViewById(R.id.question_next_button);
        submitButton = view.findViewById(R.id.question_ok_button);
        answerTextEdit = view.findViewById(R.id.question_answer_input);
        questionText = view.findViewById(R.id.question_text);
        questionProvider = new QuestionProvider();

        updateShow();
        nextButton.setOnClickListener(v -> {
            // 跳过题目视作题目错误

            question.OnError.accept(question);
            updateShow();
        });
        submitButton.setOnClickListener(v -> {
            // 获取答案
            String ans = answerTextEdit.getText().toString().trim();
            if (ans.equals("")) {
                // 验证答案不能为空
                Toast.makeText(requireContext(), "要输入答案哟~", Toast.LENGTH_SHORT).show();
                return;
            }
            // 比对答案
            if (!ans.equals(question.AnswerString)) {
                // 答案错误
                question.OnError.accept(question);
                AlertDialog dialog = new AlertDialog.Builder(requireContext())
                        .setTitle("算错了~")
                        .setMessage("正确答案是：\n\t" + question.QuestionString.replace('?', ' ') + question.AnswerString)
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialogInterface) {
                                updateShow();
                            }
                        })
                        .setNegativeButton("知道啦", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                updateShow();
                            }
                        }).create();
                dialog.show();

            } else {
                // 答案正确
                question.OnRight.accept(question);
                updateShow();
            }
        });
        return view;
    }

    private void updateShow() {
        // 更新页面
       // if (RuntimeData.Done.getValue() < RuntimeData.Target.getValue()) {
        if(1 == 1){
            question = questionProvider.generate();
            questionText.setText(question.QuestionString);
            answerTextEdit.setText("");
        } else {
            submitButton.setEnabled(false);
            nextButton.setEnabled(false);
            String s  = "今日任务已经完成!\n"+"答对数: "+RuntimeData.Correct.getValue()+"\n错误数: "+(RuntimeData.Done.getValue()-RuntimeData.Correct.getValue());
            s = s + "\n正确率: "  +(RuntimeData.Correct.getValue()*100/RuntimeData.Done.getValue()) + "%";
            questionText.setText(s);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        // TODO: Use the ViewModel
    }
}
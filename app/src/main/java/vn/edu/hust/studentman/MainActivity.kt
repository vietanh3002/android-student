package vn.edu.hust.studentman

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var studentAdapter: StudentAdapter
    private val students = mutableListOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Nguyễn Thị Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Vũ Văn Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentAdapter = StudentAdapter(students, object : StudentAdapter.OnItemClickListener {
            override fun onEditClick(position: Int) {
                editStudent(position)
            }

            override fun onDeleteClick(position: Int) {
                deleteStudent(position)
            }
        })

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_students)
        recyclerView.adapter = studentAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.btn_add_new).setOnClickListener {
            showAddStudentDialog()
        }
    }

    private fun editStudent(position: Int) {
        if (position !in students.indices) {
            // Xử lý khi position không hợp lệ
            return
        }

        val student = students[position]
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_alert_dialog)

        val name = dialog.findViewById<TextInputLayout>(R.id.name)
        val mssv = dialog.findViewById<TextInputLayout>(R.id.mssv)
        val btnCancel = dialog.findViewById<Button>(R.id.button_cancel)
        val btnOk = dialog.findViewById<Button>(R.id.button_ok)

        // Thiết lập giá trị hiện tại của sinh viên vào các trường nhập liệu
        name.editText?.setText(student.studentName)
        mssv.editText?.setText(student.studentId)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnOk.setOnClickListener {
            val updatedName = name.editText?.text.toString()
            val updatedMssv = mssv.editText?.text.toString()

            // Cập nhật thông tin sinh viên trong danh sách
            val updatedStudent = StudentModel(updatedName, updatedMssv)
            students[position] = updatedStudent

            // Thông báo Adapter rằng mục đã thay đổi
            studentAdapter.notifyItemChanged(position)

            dialog.dismiss()
        }

        dialog.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        dialog.show()
    }

    private fun deleteStudent(position: Int) {
        if (position !in students.indices) {
            return
        }

        // Lưu sinh viên bị xóa để có thể hoàn tác
        val deletedStudent = students[position]
        
        // Xóa sinh viên khỏi danh sách
        students.removeAt(position)
        studentAdapter.notifyItemRemoved(position)

        // Hiển thị Snackbar với nút hoàn tác
        Snackbar.make(
            findViewById(R.id.recycler_view_students),
            "Đã xóa sinh viên ${deletedStudent.studentName}",
            Snackbar.LENGTH_LONG
        ).setAction("Hoàn tác") {
            // Thêm lại sinh viên vào vị trí cũ
            students.add(position, deletedStudent)
            studentAdapter.notifyItemInserted(position)
        }.show()
    }

    private fun showAddStudentDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_alert_dialog)
        val name = dialog.findViewById<TextInputLayout>(R.id.name)
        val mssv = dialog.findViewById<TextInputLayout>(R.id.mssv)
        val btnCancel = dialog.findViewById<Button>(R.id.button_cancel)
        val btnOk = dialog.findViewById<Button>(R.id.button_ok)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnOk.setOnClickListener {
            val studentName = name.editText?.text.toString()
            val studentId = mssv.editText?.text.toString()

            val newStudent = StudentModel(studentName, studentId)
            students.add(newStudent)
            studentAdapter.notifyItemInserted(students.size - 1)

            dialog.dismiss()
        }

        dialog.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        dialog.show()
    }
}
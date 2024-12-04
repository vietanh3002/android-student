package vn.edu.hust.studentman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val students: MutableList<StudentModel>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    interface OnItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
        val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
        val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
        val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)

        init {
            imageEdit.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onEditClick(position)
                }
            }

            imageRemove.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item,
            parent, false)
        return StudentViewHolder(itemView)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.textStudentName.text = student.studentName
        holder.textStudentId.text = student.studentId
    }

    fun updateStudent(position: Int, student: StudentModel) {
        students[position] = student
        notifyItemChanged(position)
    }

    fun removeStudent(position: Int): StudentModel {
        val removed = students.removeAt(position)
        notifyItemRemoved(position)
        return removed
    }

    fun addStudent(student: StudentModel, position: Int) {
        students.add(position, student)
        notifyItemInserted(position)
    }
}
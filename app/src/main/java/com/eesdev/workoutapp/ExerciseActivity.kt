package com.eesdev.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.eesdev.workoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding : ActivityExerciseBinding
    //private lateinit var exerciseList : ArrayList<String>
    private lateinit var exerciseMap : Map<String, String>
    override fun onCreate(savedInstanceState: Bundle?) {

        val db = DatabaseHandler(this,null)
        exerciseMap = mapOf(
            "Bicep Curl" to "The term \"biceps curl\" refers to any of a number of weight training exercises that primarily targets the biceps brachii muscle. It may be performed using a barbell, dumbbell, resistance band, or other equipment.",
            "Skull Crusher" to "A skull crusher, also known as a lying triceps extension, is an isolation exercise focused on your triceps muscles. Skull crushers are performed by lying on your back on a flat bench and lifting dumbbells from behind your head to full extension above you.",
            "Push Ups" to "The push-up is a common calisthenics exercise beginning from the prone position. By raising and lowering the body using the arms, push-ups exercise the pectoral muscles, triceps, and anterior deltoids, with ancillary benefits to the rest of the deltoids, serratus anterior, coracobrachialis and the midsection as a whole.",
            "Pull Ups" to "A pull-up is an upper-body strength exercise. The pull-up is a closed-chain movement where the body is suspended by the hands, gripping a bar or other implement at a distance typically wider than shoulder-width, and pulled up.",
            "Chin-ups" to "The chin up is a variation of the pull up and an exercise used to build the muscles of the back - particularly the lats. The chin up differs from the pull up in that the chin up allows for a greater degree of bicep involvement during the pulling movement.",
            "Dips" to "A dip is an upper-body strength exercise. Narrow, shoulder-width dips mainly trains the triceps, with major synergists being the anterior deltoid, the pectoralis muscles, and the rhomboid muscles of the back.",
            "Preacher Curl" to "The preacher curl is a variation of the traditional biceps curl. It is an isolation bicep exercise that allows you to practice your lifting form with a controlled movement supported by a preacher bench. Perform preacher curls by sitting on a preacher bench with your armpits resting on top of the arm pad.",
            "Triceps Extension" to "The Tricep Extension is an isolation movement that helps develop and strengthen the triceps, the muscles that run along the back of your upper arm. You can practice this exercise standing or sitting on a bench for back support.",
            "Romanian Deadlift" to "The Romanian deadlift (RDL) is a traditional barbell lift used to develop the strength of the posterior chain muscles, including the erector spinae, gluteus maximus, hamstrings and adductors. When done correctly, the RDL is an effective exercise that helps strengthen both the core and the lower body with one move.",
            "Hamstring Curl" to "The leg curl is an isolation exercise that targets the hamstring muscles. The exercise involves flexing the lower leg against resistance towards the buttocks",
            "Leg Press" to "The leg press is a compound weight training exercise in which the individual pushes a weight or resistance away from them using their legs. The term leg press machine refers to the apparatus used to perform this exercise.",
            "Leg Extension" to "The leg extension is a resistance weight training exercise that targets the quadriceps muscle in the legs. The exercise is done using a machine called the Leg Extension Machine.",
            "Chest Fly" to "A chest fly is a weightlifting exercise that primarily targets the pectoral muscles. It is a variation of the standard bench press and is performed by lying on a flat bench with a weight in each hand. You can do this exercise with dumbbells, barbells, or cables.",
            "Face Pull" to "The face pull is a weight training exercise that primarily targets the musculature of the upper back and shoulders, namely the posterior deltoids, trapezius, rhomboids, as well as the infraspinatus and teres minor muscles of the rotator cuff.",
            "Dumbell Press" to "The dumbbell bench press, also known as the dumbbell flat bench press and the dumbbell chest press, is an upper-body exercise that activates your arm, shoulder, and chest muscles. Perform the dumbbell bench press exercise by lying flat on your back on a bench.",
            "Incline Dumbell Press" to "The incline dumbbell press, also known as the incline chest press and the incline dumbbell bench press, is an upper body workout that engages the pectoral muscles in your chest, the triceps on the backside of your arms, and the anterior deltoid muscles on the front of your shoulders.",
            "Seated Cable Row" to "A seated cable row is a compound exercise that utilizes a weighted horizontal cable row machine to work muscle groups in your back and arms. Cable machines include a bench for comfortable seating and foot plates to brace yourself against as you pull the weighted cable.",
            "Lateral Raise" to "A lat raise is an isolation exercise for the upper body that works the shoulder muscles. Lat, or lateral raises, can be performed with dumbbells, cable pulleys, or using a lateral raise machine at the gym. They can also be performed with no weight for beginners.",
            "Barbell Row" to "A bent-over row is a weight training exercise that targets a variety of back muscles. Which ones are targeted varies on form. The bent over row is often used for both bodybuilding and powerlifting.",
            "Dumbell Curl" to "The term \"biceps curl\" refers to any of a number of weight training exercises that primarily targets the biceps brachii muscle. It may be performed using a barbell, dumbbell, resistance band, or other equipment.",
            "Overhead Press" to "The overhead press is an upper-body weight training exercise in which the trainee presses a weight overhead while seated or standing. It is mainly used to develop the anterior deltoid muscles of the shoulder.",
            "Conventional Deadlift" to "A conventional deadlift is when the lifter keeps their feet about shoulder-width apart and lifts with their hands outside their knees.",
            "Sumo Deadlift" to "The Sumo deadlift is a variation of the conventional deadlift often adopted by powerlifters. The difference between the two lies in the setup of the lifter's feet and hands. When the bar is gripped with the lifter's hands inside their legs, the form is considered \"Sumo\".",
            "Low Bar Squat" to "A low bar squat is a squat in which the bar is placed low on the upper back in the back squat position. It should be resting on the posterior deltoid, not the top of the shoulders.",
            "High Bar Squat" to "A high bar squat is a back squat where the bar is placed high on the trapezius muscle across the top of the shoulders. The feet are shoulder-width apart with toes pointed slightly outward",
            "Incline Bench Press" to "The incline bench press is a variation of the bench press and an exercise used to build the muscles of the chest. The shoulders and triceps will be indirectly involved as well. Utilizing an incline will allow you to better target the upper portion of the chest, a lagging part for a lot of lifters.",
            "Bench Press" to "The bench press, or chest press, is a weight training exercise in which the trainee presses a weight upwards while lying on a weight training bench."
        )

        db.addExercise(exerciseMap)

        binding = ActivityExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        super.onCreate(savedInstanceState)
        setContentView(view)
        binding.recyclerExercises.layoutManager=LinearLayoutManager(this)
        binding.recyclerExercises.adapter = ExerciseAdapter(exerciseMap,globalEmail!!, globalDay!!)
        binding.buttonBack.setOnClickListener {
            val intent = Intent(this,ProgramActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        //back button disabled
    }
}
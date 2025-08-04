package com.example.firebase;

import java.io.IOException;
import java.util.ArrayList;

import com.example.classes.*;
import com.example.ToControlDatabase.*;

import com.google.cloud.firestore.Firestore;

public class MainForFirestore {
    public static void main(String[] args) throws IOException {
        System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", "src/main/resources/serviceAccountKey.json");
        Firestore db = FirestoreHelper.getFirestore();
        Course c = Finder.courseCodeFinder("CS101");
        User u = Finder.UserFinder("urcan23");

        System.out.println(c);
        System.out.println(c);
        System.out.println(c);
        System.out.println(c);
        System.out.println(c);

        ArrayList<Course> desiredCourses = new ArrayList<>();
        desiredCourses.add(c);

        ArrayList<User> friends = new ArrayList<>();
        friends.add(u);
        
        FirestoreUtils.addCoursestoTheFirestore(db, "CS", "CS101", "Introduction to Programming");
        FirestoreUtils.addCoursestoTheFirestore(db, "CS", "CS102", "Data Structures and Algorithms");
        FirestoreUtils.addCoursestoTheFirestore(db, "CS", "CS103", "Discrete Mathematics for CS");
        FirestoreUtils.addCoursestoTheFirestore(db, "CS", "CS104", "Computer Architecture");
        FirestoreUtils.addCoursestoTheFirestore(db, "CS", "CS105", "Object-Oriented Programming with Java");
        FirestoreUtils.addCoursestoTheFirestore(db, "CS", "CS106", "Introduction to Web Development");
        FirestoreUtils.addCoursestoTheFirestore(db, "EE", "EE101", "Introduction to Electrical Engineering");
        FirestoreUtils.addCoursestoTheFirestore(db, "EE", "EE102", "Digital Logic Design");
        FirestoreUtils.addCoursestoTheFirestore(db, "EE", "EE103", "Circuit Analysis");
        FirestoreUtils.addCoursestoTheFirestore(db, "EE", "EE104", "Signals and Systems");
        FirestoreUtils.addCoursestoTheFirestore(db, "EE", "EE105", "Electronics I");
        FirestoreUtils.addCoursestoTheFirestore(db, "EE", "EE106", "Introduction to Embedded Systems");

        FirestoreUtils.addCoursestoTheFirestore(db, "MATH", "MATH101", "Calculus I");
        FirestoreUtils.addCoursestoTheFirestore(db, "MATH", "MATH102", "Calculus II");
        FirestoreUtils.addCoursestoTheFirestore(db, "MATH", "MATH103", "Linear Algebra");
        FirestoreUtils.addCoursestoTheFirestore(db, "MATH", "MATH104", "Differential Equations");
        FirestoreUtils.addCoursestoTheFirestore(db, "MATH", "MATH105", "Discrete Mathematics");
        FirestoreUtils.addCoursestoTheFirestore(db, "MATH", "MATH106", "Probability and Statistics");

        FirestoreUtils.addCoursestoTheFirestore(db, "PHYS", "PHYS101", "Physics I: Mechanics");
        FirestoreUtils.addCoursestoTheFirestore(db, "PHYS", "PHYS102", "Physics II: Electricity and Magnetism");
        FirestoreUtils.addCoursestoTheFirestore(db, "PHYS", "PHYS103", "Introduction to Thermodynamics");
        FirestoreUtils.addCoursestoTheFirestore(db, "PHYS", "PHYS104", "Waves and Optics");
        FirestoreUtils.addCoursestoTheFirestore(db, "PHYS", "PHYS105", "Modern Physics");
        FirestoreUtils.addCoursestoTheFirestore(db, "PHYS", "PHYS106", "Physics Laboratory I");
        FirestoreUtils.addCoursestoTheFirestore(db, "IE", "IE101", "Introduction to Industrial Engineering");
        FirestoreUtils.addCoursestoTheFirestore(db, "IE", "IE102", "Operations Research I");
        FirestoreUtils.addCoursestoTheFirestore(db, "IE", "IE103", "Engineering Economy");
        FirestoreUtils.addCoursestoTheFirestore(db, "IE", "IE104", "Production Systems");
        FirestoreUtils.addCoursestoTheFirestore(db, "IE", "IE105", "Work Study and Ergonomics");
        FirestoreUtils.addCoursestoTheFirestore(db, "IE", "IE106", "Statistics for Engineers");

        FirestoreUtils.addCoursestoTheFirestore(db, "ME", "ME101", "Introduction to Mechanical Engineering");
        FirestoreUtils.addCoursestoTheFirestore(db, "ME", "ME102", "Statics");
        FirestoreUtils.addCoursestoTheFirestore(db, "ME", "ME103", "Dynamics");
        FirestoreUtils.addCoursestoTheFirestore(db, "ME", "ME104", "Thermodynamics");
        FirestoreUtils.addCoursestoTheFirestore(db, "ME", "ME105", "Fluid Mechanics");
        FirestoreUtils.addCoursestoTheFirestore(db, "ME", "ME106", "Strength of Materials");

    }
}

_Note: This file is written in MarkDown for improved readability. 
A plaintext version is included as `README.txt`_

**When desigining the data model for our project, we defined the following Classes:**

- _`Entity`_ - Base class for Person, Pet, Possession, and Moment. Encapsulates `name` and `image` fields, providing a 
2-parameter constructor and getters.
- _`LivingEntity`_ - Extends `Entity`, encapsulates common functionality between the Pet and Person classes. This also
provides a common type for defining and iterating over ArrayLists containing Persons and Pets.


**In addition, we defined the following Interface:**

- _`Ownable`_ - Defines the common fields and functionality between a Pet and a Possession. This also provides a common
type for defining and iterating over ArrayLists containing Pets and Possessions.
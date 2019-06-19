//import * as jsdiff from 'diff'

class PatchController {
	static createPatch(oldDoc, newDoc) {
		console.log("create patch method is called!")
		console.log(newDoc)

		// // Make sure nativeLog is defined and is a function
        // if (typeof nativeLog === 'function') {
		// 	nativeLog('Createing patch')
        //     nativeLog(`Creating patch for text '${newDoc}'`)
        // }

		let patch = JsDiff.createPatch("newpatch", oldDoc, newDoc, "", "")
		return patch
	}

	static test() {
		return "testing static methods";
	}
}